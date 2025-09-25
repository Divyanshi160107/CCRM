package edu.ccrm.exception;

/**
 * A custom exception thrown when an attempt is made to enroll a student
 * in a course they are already enrolled in.
 */
public class DuplicateEnrollmentException extends Exception {

    /**
     * Constructor for the exception.
     * @param message The detail message explaining why the exception occurred.
     */
    public DuplicateEnrollmentException(String message) {
        // Call the constructor of the parent class (Exception)
        super(message);
    }
}