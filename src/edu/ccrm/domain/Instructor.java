package edu.ccrm.domain;

// The Instructor class extends Person, demonstrating Inheritance
public class Instructor extends Person {
    
    // Instructor-specific field
    private String employeeId;

    /**
     * Constructor for the Instructor class.
     */
    public Instructor(String fullName, String email, String employeeId) {
        // This MUST be the first line
        super(fullName, email);
        this.employeeId = employeeId;
    }

    /**
     * Provides the specific implementation for the abstract method from Person.
     */
    @Override
    public void printDetails() {
        System.out.println("Instructor Name: " + getFullName() + ", Employee ID: " + this.employeeId);
    }

    // Getter for the instructor-specific field
    public String getEmployeeId() {
        return employeeId;
    }
}