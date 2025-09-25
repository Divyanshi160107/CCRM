package edu.ccrm.domain;

import java.time.LocalDate;

/**
 * Represents the enrollment of a single student in a single course.
 * It holds the student, the course, and the grade received.
 */
public class Enrollment {
    private final Student student;
    private final Course course;
    private final LocalDate enrollmentDate;
    private Grade grade; // Grade is not final, as it will be assigned later

    /**
     * Constructor for a new enrollment.
     * The grade is initially set to null.
     * @param student The student who is enrolling.
     * @param course The course they are enrolling in.
     */
    public Enrollment(Student student, Course course) {
        this.student = student;
        this.course = course;
        this.enrollmentDate = LocalDate.now();
        this.grade = null; // No grade is assigned upon enrollment
    }

    // --- Getters and Setters ---

    public Student getStudent() {
        return student;
    }

    public Course getCourse() {
        return course;
    }

    public Grade getGrade() {
        return grade;
    }

    // Only the grade can be changed after an enrollment is created.
    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }
}