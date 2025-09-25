package edu.ccrm.domain;

import java.time.LocalDate;

public class Student extends Person {
    
    private String regNo;
    private String status;
    private LocalDate enrollmentDate;

    public Student(String fullName, String email, String regNo) {
        super(fullName, email);
        this.regNo = regNo;
        this.status = "Active";
        this.enrollmentDate = LocalDate.now();
    }

    @Override
    public void printDetails() {
        System.out.println("Student Name: " + getFullName());
        System.out.println("Email: " + getEmail());
        System.out.println("Registration No: " + this.regNo);
        System.out.println("Status: " + this.status);
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    // --- NEW: Non-Static Inner Class ---
    /**
     * An inner class to manage enrollment-related actions for THIS specific student.
     * It has direct access to the outer Student's fields.
     */
    public class EnrollmentManager {
        public void displayEnrollmentInfo() {
            // Note how this method can access the private fields of the outer Student class
            System.out.println("Managing enrollments for student: " + fullName + " (" + regNo + ")");
        }
    }

    // A helper method to get an instance of the inner class
    public EnrollmentManager getEnrollmentManager() {
        return new EnrollmentManager();
    }
}