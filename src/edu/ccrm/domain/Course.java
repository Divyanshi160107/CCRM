package edu.ccrm.domain;

public class Course {

    private final String code;
    private final String title;
    private final int credits;
    private final String instructor;
    private final Semester semester;
    private final String department;
    private String status; // NEW FIELD

    private Course(Builder builder) {
        this.code = builder.code;
        this.title = builder.title;
        this.credits = builder.credits;
        this.instructor = builder.instructor;
        this.semester = builder.semester;
        this.department = builder.department;
        this.status = "Active"; // Default status
    }

    // --- Getters and a new Setter for status ---
    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }
    public String getInstructor() { return instructor; }
    public Semester getSemester() { return semester; }
    public String getDepartment() { return department; }
    public String getStatus() { return status; } // NEW GETTER
    public void setStatus(String status) { this.status = status; } // NEW SETTER
    
    @Override
    public String toString() {
        return "Course [code=" + code + ", title=" + title + ", status=" + status + "]";
    }

    public static class Builder {
        private final String code;
        private final String title;
        private int credits;
        private String instructor;
        private Semester semester;
        private String department;

        public Builder(String code, String title) {
            this.code = code;
            this.title = title;
        }

        public Builder credits(int credits) {
            this.credits = credits;
            return this;
        }

        public Builder instructor(String instructor) {
            this.instructor = instructor;
            return this;
        }

        public Builder semester(Semester semester) {
            this.semester = semester;
            return this;
        }
        
        public Builder department(String department) {
            this.department = department;
            return this;
        }

        public Course build() {
            return new Course(this);
        }
    }
}