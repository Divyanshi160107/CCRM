package edu.ccrm.io;

import edu.ccrm.config.AppConfig;
import edu.ccrm.domain.*;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.EnrollmentService;
import edu.ccrm.service.StudentService;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

public class ImportExportService {
    // Use the Singleton AppConfig to get the data directory path
    private static final Path DATA_DIRECTORY = Paths.get(AppConfig.getInstance().getDataDirectory());
    private static final Path STUDENTS_FILE = DATA_DIRECTORY.resolve("students.csv");
    private static final Path COURSES_FILE = DATA_DIRECTORY.resolve("courses.csv");
    private static final Path ENROLLMENTS_FILE = DATA_DIRECTORY.resolve("enrollments.csv");

    /**
     * Saves all application data to CSV files. This is the exportData method.
     */
    public void exportData(List<Student> students, List<Course> courses, List<Enrollment> enrollments) {
        try {
            if (Files.notExists(DATA_DIRECTORY)) {
                Files.createDirectories(DATA_DIRECTORY);
            }

            // --- Save Students ---
            try (BufferedWriter writer = Files.newBufferedWriter(STUDENTS_FILE)) {
                for (Student student : students) {
                    writer.write(String.join(",", student.getRegNo(), student.getFullName(), student.getEmail()));
                    writer.newLine();
                }
            }

            // --- Save Courses ---
            try (BufferedWriter writer = Files.newBufferedWriter(COURSES_FILE)) {
                for (Course course : courses) {
                    writer.write(String.join(",", course.getCode(), course.getTitle(), 
                        String.valueOf(course.getCredits()), course.getInstructor(), 
                        course.getSemester().name(), course.getDepartment()));
                    writer.newLine();
                }
            }

            // --- Save Enrollments ---
            try (BufferedWriter writer = Files.newBufferedWriter(ENROLLMENTS_FILE)) {
                for (Enrollment enr : enrollments) {
                    String grade = (enr.getGrade() == null) ? "NULL" : enr.getGrade().name();
                    writer.write(String.join(",", enr.getStudent().getRegNo(), enr.getCourse().getCode(), grade));
                    writer.newLine();
                }
            }
            System.out.println("Data successfully saved.");
        } catch (IOException e) {
            System.err.println("Error saving data: " + e.getMessage());
        }
    }

    /**
     * Loads all application data from CSV files. This is the importData method.
     */
    public void importData(StudentService ss, CourseService cs, EnrollmentService es) {
        if (Files.notExists(DATA_DIRECTORY)) {
            System.out.println("Data directory not found. Starting with a clean slate.");
            return;
        }

        // --- Load Students ---
        try (Stream<String> lines = Files.lines(STUDENTS_FILE)) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    ss.addStudent(parts[1], parts[2], parts[0]);
                }
            });
        } catch (IOException e) { /* Silently ignore if file doesn't exist */ }
        
        // --- Load Courses ---
        try (Stream<String> lines = Files.lines(COURSES_FILE)) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 6) {
                    cs.addCourse(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3], 
                        Semester.valueOf(parts[4]), parts[5]);
                }
            });
        } catch (IOException e) { /* Silently ignore if file doesn't exist */ }

        // --- Load Enrollments ---
        try (Stream<String> lines = Files.lines(ENROLLMENTS_FILE)) {
            lines.forEach(line -> {
                String[] parts = line.split(",");
                if (parts.length == 3) {
                    Student student = ss.findStudent(parts[0]);
                    Course course = cs.findCourseByCode(parts[1]);
                    if (student != null && course != null) {
                        try {
                            es.enrollStudent(student, course);
                            if (!"NULL".equals(parts[2])) {
                                es.recordGrade(student, course, Grade.valueOf(parts[2]));
                            }
                        } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
                            // Silently ignore expected errors on load
                        }
                    }
                }
            });
        } catch (IOException e) { /* Silently ignore if file doesn't exist */ }
        
        System.out.println("Data loaded successfully.");
    }
}