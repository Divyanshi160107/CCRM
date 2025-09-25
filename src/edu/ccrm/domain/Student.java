package edu.ccrm.domain;

import java.time.LocalDate;

public class Student extends Person {

    // Add the student-specific fields here
    private String regNo;
    private String status;
    private LocalDate enrollmentDate;

    // Add the constructor here
    public Student(String fullName, String email, String regNo) {
        super(fullName, email);
        this.regNo = regNo;
        this.status = "Active";
        this.enrollmentDate = LocalDate.now();
    }

    // Add the implemented method here
    @Override
    public void printDetails() {
        System.out.println("Student Name: " + getFullName());
        System.out.println("Registration No: " + this.regNo);
        System.out.println("Status: " + this.status);
    }

    // Add any other getters and setters here
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
} // The final closing brace of the Student class