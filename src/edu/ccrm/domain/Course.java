package edu.ccrm.domain;

public class Course {

    // --- Fields for the Course ---
    private final String code;
    private final String title;
    private final int credits;
    private final String instructor; // For now, we use a String. Later, this could be an Instructor object.
    private final Semester semester;
    private final String department;

    // --- 1. A PRIVATE constructor ---
    // This prevents creating a Course object directly using "new Course()".
    // You MUST use the builder.
    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
    }

    // --- Getters for all fields (no setters to make it immutable) ---
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }

    @Override
    public String toString() {
        return "Course [code=" + code + ", title=" + title + "]";
    }


    // --- 2. A static nested Builder class ---
    public static class Builder {

        // It has the same fields as the Course class.
        // Required fields are final, optional are not.
        private final String code;
        private final String title;

        private int credits;
        private String instructor;
        private Semester semester;
        private String department;

        // The Builder's constructor only takes the required fields.
        public Builder(String code, String title) {
            this.code = code;
            this.title = title;
        }

        // "Setter" methods for the optional fields.
        // They each return the Builder object to allow for "chaining".
        public Builder credits(int credits) {
            this.credits = credits;
            return this; // Return the builder instance
        }

        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this; // Return the builder instance
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this; // Return the builder instance
        }

        public Builder department(String department) {
            this.department = department;
            return this; // Return the builder instance
        }

        // --- 3. The build() method ---
        // This is the final step. It creates the actual Course object.
        public Course build() {
            return new Course(this);
        }
    }
}