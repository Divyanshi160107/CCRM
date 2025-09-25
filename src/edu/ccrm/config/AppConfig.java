package edu.ccrm.config;

public class AppConfig {

    // 1. The single, private, static instance of the class
    private static AppConfig instance;

    // A sample configuration setting
    private final String dataDirectory = "ccrm-data";

    // 2. A private constructor to prevent anyone else from creating an instance
    private AppConfig() {
        // Initialization code can go here
    }

    // 3. The public, static method to get the single instance
    public static AppConfig getInstance() {
        // Create the instance only if it doesn't exist yet (lazy initialization)
        if (instance == null) {
            instance = new AppConfig();
        }
        return instance;
    }

    // Getter for the sample setting
    public String getDataDirectory() {
        return dataDirectory;
    }
}