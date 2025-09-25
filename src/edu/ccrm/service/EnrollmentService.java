package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class EnrollmentService {

    private final List<Enrollment> enrollments = new ArrayList<>();
    private static final int MAX_CREDITS = 20;

    public void enrollStudent(Student student, Course course) 
        throws DuplicateEnrollmentException, MaxCreditLimitExceededException {

        int currentCredits = getEnrollmentsForStudent(student).stream()
            .mapToInt(enr -> enr.getCourse().getCredits())
            .sum();
        
        if (currentCredits + course.getCredits() > MAX_CREDITS) {
            throw new MaxCreditLimitExceededException(
                "Cannot enroll. Exceeds max credit limit of " + MAX_CREDITS
            );
        }

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().equals(student) && enrollment.getCourse().equals(course)) {
                throw new DuplicateEnrollmentException(
                    student.getFullName() + " is already enrolled in " + course.getTitle()
                );
            }
        }
        
        Enrollment newEnrollment = new Enrollment(student, course);
        enrollments.add(newEnrollment);
        System.out.println("Enrollment successful!");
    }

    public boolean unenrollStudent(Student student, Course course) {
        return enrollments.removeIf(
            enrollment -> enrollment.getStudent().equals(student) && enrollment.getCourse().equals(course)
        );
    }

    public boolean recordGrade(Student student, Course course, Grade grade) {
        assert student != null : "Student parameter cannot be null when recording a grade.";
        assert course != null : "Course parameter cannot be null when recording a grade.";
        assert grade != null : "Grade parameter cannot be null when recording a grade.";

        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudent().equals(student) && enrollment.getCourse().equals(course)) {
                enrollment.setGrade(grade);
                return true;
            }
        }
        return false;
    }

    public List<Enrollment> getEnrollmentsForStudent(Student student) {
        return enrollments.stream()
            .filter(enrollment -> enrollment.getStudent().equals(student))
            .collect(Collectors.toList());
    }
    
    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }
}