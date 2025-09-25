package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    // A list to act as a temporary, in-memory database for students.
    private final List<Student> students = new ArrayList<>();

    /**
     * Adds a new student to our list.
     * @param fullName The student's full name.
     * @param email The student's email.
     * @param regNo The student's registration number.
     * @return The newly created Student object.
     */
    public Student addStudent(String fullName, String email, String regNo) {
        Student newStudent = new Student(fullName, email, regNo);
        students.add(newStudent);
        System.out.println("Student added successfully: " + fullName);
        return newStudent;
    }

    /**
     * Finds a student by their registration number.
     * @param regNo The registration number to search for.
     * @return The Student object if found, or null if not found.
     */
    public Student findStudentByRegNo(String regNo) {
        // Loop through all students in the list.
        for (Student student : students) {
            // If a student's regNo matches, return that student.
            if (student.getRegNo().equalsIgnoreCase(regNo)) {
                return student;
            }
        }
        // If the loop finishes and no student was found, return null.
        return null;
    }

    /**
     * Returns a copy of the list of all students.
     * @return A list of all students.
     */
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return a copy to prevent outside modification.
    }
}