package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class BackupService {

    private static final Path SOURCE_DIR = Paths.get(AppConfig.getInstance().getDataDirectory());
    private static final Path BACKUP_ROOT_DIR = Paths.get("backups");

    /**
     * Creates a new backup of the data directory in a timestamped subfolder.
     */
    public void createBackup() {
        // Create a timestamp string like "2025-09-25_17-30-00"
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path backupDir = BACKUP_ROOT_DIR.resolve(timestamp);

        try {
            // Ensure the source directory exists before trying to back it up
            if (Files.notExists(SOURCE_DIR)) {
                System.out.println("Data directory not found. Nothing to back up.");
                return;
            }

            // Create the new timestamped backup directory
            Files.createDirectories(backupDir);

            // Walk through all files in the source data directory
            try (Stream<Path> stream = Files.walk(SOURCE_DIR)) {
                stream
                    .filter(Files::isRegularFile)
                    .forEach(sourceFile -> {
                        try {
                            // Copy each file to the new backup directory
                            Path destinationFile = backupDir.resolve(SOURCE_DIR.relativize(sourceFile));
                            Files.copy(sourceFile, destinationFile, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e) {
                            System.err.println("Failed to copy file: " + sourceFile);
                        }
                    });
            }
            System.out.println("Backup created successfully at: " + backupDir.toAbsolutePath());

        } catch (IOException e) {
            System.err.println("Could not create backup: " + e.getMessage());
        }
    }

    /**
     * Recursively calculates the total size of a directory.
     * This implementation uses Files.walk() which handles the recursion internally.
     * @param path The path to the directory.
     * @return The total size in bytes.
     */
    public long calculateDirectorySize(Path path) {
        try (Stream<Path> walk = Files.walk(path)) {
            return walk
                .filter(Files::isRegularFile)
                .mapToLong(p -> {
                    try {
                        return Files.size(p);
                    } catch (IOException e) {
                        return 0L; // File size cannot be read
                    }
                })
                .sum();
        } catch (IOException e) {
            System.err.println("Could not calculate directory size: " + e.getMessage());
            return 0L;
        }
    }
}