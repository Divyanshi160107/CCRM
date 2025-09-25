package edu.ccrm.service;

import edu.ccrm.domain.Student;
import java.util.List;

public interface StudentService {
    Student addStudent(String fullName, String email, String regNo);
    List<Student> getAllStudents();
    boolean updateStudent(String regNo, String newFullName, String newEmail);
    boolean deactivateStudent(String regNo);
    Student findStudent(String regNo);
    Student findStudent(int numericId);
}