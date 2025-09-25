package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.ArrayList;
import java.util.List;

/**
 * The implementation of the StudentService interface. This class contains the
 * actual logic for managing students.
 */
public class StudentServiceImpl implements StudentService {

    private final List<Student> students = new ArrayList<>();

    @Override
    public Student addStudent(String fullName, String email, String regNo) {
        Student newStudent = new Student(fullName, email, regNo);
        students.add(newStudent);
        System.out.println("Student added successfully: " + fullName);
        return newStudent;
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    @Override
    public boolean updateStudent(String regNo, String newFullName, String newEmail) {
        Student studentToUpdate = findStudent(regNo);
        if (studentToUpdate != null) {
            studentToUpdate.setFullName(newFullName);
            studentToUpdate.setEmail(newEmail);
            return true;
        }
        return false;
    }

    @Override
    public boolean deactivateStudent(String regNo) {
        Student studentToDeactivate = findStudent(regNo);
        if (studentToDeactivate != null) {
            studentToDeactivate.setStatus("Inactive");
            return true;
        }
        return false;
    }

    @Override
    public Student findStudent(String regNo) {
        for (Student student : students) {
            if (student.getRegNo().equalsIgnoreCase(regNo)) {
                return student;
            }
        }
        return null;
    }

    @Override
    public Student findStudent(int numericId) {
        System.out.println("Searching for student with numeric ID: " + numericId + " (demonstration only)");
        return null;
    }
}