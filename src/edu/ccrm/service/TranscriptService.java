package edu.ccrm.service;

import edu.ccrm.domain.Enrollment;
import edu.ccrm.domain.Grade;
import edu.ccrm.domain.Student;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TranscriptService {

    private double calculateGPA(List<Enrollment> enrollments) {
        double totalPoints = 0;
        int totalCredits = 0;
        
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getGrade() != null) {
                int credits = enrollment.getCourse().getCredits();
                double gradePoints = enrollment.getGrade().getPoints();
                
                totalPoints += credits * gradePoints;
                totalCredits += credits;
            }
        }
        
        if (totalCredits == 0) {
            return 0.0;
        }
        
        return totalPoints / totalCredits;
    }

    public void printTranscript(Student student, List<Enrollment> enrollments) {
        System.out.println("\n--- ACADEMIC TRANSCRIPT ---");
        student.printDetails();
        System.out.println("---------------------------");
        System.out.println("Courses Enrolled:");

        if (enrollments.isEmpty()) {
            System.out.println("No courses enrolled.");
        } else {
            for (Enrollment enr : enrollments) {
                String grade = (enr.getGrade() == null) ? "Not Graded" : enr.getGrade().toString();
                System.out.println(
                    " - " + enr.getCourse().getCode() + ": " + 
                    enr.getCourse().getTitle() + " | Credits: " + 
                    enr.getCourse().getCredits() + " | Grade: " + grade
                );
            }
        }
        
        double gpa = calculateGPA(enrollments);
        DecimalFormat df = new DecimalFormat("#.##");

        System.out.println("---------------------------");
        System.out.println("Cumulative GPA: " + df.format(gpa));
        System.out.println("--- END OF TRANSCRIPT ---");
    }

    public void generateGpaDistributionReport(List<Enrollment> allEnrollments) {
        System.out.println("\n--- GPA Distribution Report ---");
        if (allEnrollments.isEmpty()) {
            System.out.println("No enrollment data available to generate a report.");
            return;
        }

        Map<Grade, Long> gradeDistribution = allEnrollments.stream()
            .filter(enr -> enr.getGrade() != null)
            .collect(Collectors.groupingBy(
                Enrollment::getGrade,
                Collectors.counting()
            ));

        if (gradeDistribution.isEmpty()) {
            System.out.println("No grades have been recorded yet.");
        } else {
            System.out.println("Number of students per grade:");
            gradeDistribution.forEach((grade, count) -> {
                System.out.println("Grade " + grade + ": " + count + " student(s)");
            });
        }
        System.out.println("-----------------------------");
    }
}