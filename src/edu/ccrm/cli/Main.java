package edu.ccrm.cli;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import edu.ccrm.domain.Student;
import edu.ccrm.service.CourseService;
import edu.ccrm.service.StudentService;
import java.util.List;
import java.util.Scanner;

public class Main {

    /**
     * The main entry point for the application.
     */
    public static void main(String[] args) {
        // --- 1. Initialize Services and Scanner ---
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Scanner scanner = new Scanner(System.in);

        // --- 2. Main Application Loop ---
        while (true) {
            // --- 3. Display the Menu ---
            System.out.println("\n--- Campus Course & Records Manager ---");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            // --- 4. Get User Input ---
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            // --- 5. Process Choice with a Switch Statement ---
            switch (choice) {
                case 1:
                    manageStudents(scanner, studentService);
                    break;
                case 2:
                    manageCourses(scanner, courseService);
                    break;
                case 3:
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close(); // Close the scanner before exiting
                    return; // Exit the main method, which ends the program
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    /**
     * Handles the sub-menu and logic for student management.
     */
    private static void manageStudents(Scanner scanner, StudentService studentService) {
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Find Student by Registration No.");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter full name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();
                    System.out.print("Enter registration number: ");
                    String regNo = scanner.nextLine();
                    studentService.addStudent(name, email, regNo);
                    break;
                case 2:
                    System.out.println("\n--- List of All Students ---");
                    List<Student> students = studentService.getAllStudents();
                    if (students.isEmpty()) {
                        System.out.println("No students found.");
                    } else {
                        for (Student student : students) {
                            student.printDetails();
                            System.out.println("--------------------");
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter registration number to find: ");
                    String searchRegNo = scanner.nextLine();
                    Student foundStudent = studentService.findStudentByRegNo(searchRegNo);
                    if (foundStudent != null) {
                        System.out.println("\n--- Student Found ---");
                        foundStudent.printDetails();
                        System.out.println("---------------------");
                    } else {
                        System.out.println("Student with registration number '" + searchRegNo + "' not found.");
                    }
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    return; // Exit this method to go back
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    /**
     * Handles the sub-menu and logic for course management.
     */
    private static void manageCourses(Scanner scanner, CourseService courseService) {
        while (true) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add New Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Courses by Title");
            System.out.println("4. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter course code (e.g., CS101): ");
                    String code = scanner.nextLine();
                    System.out.print("Enter course title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter credits: ");
                    int credits = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
                    System.out.print("Enter instructor name: ");
                    String instructor = scanner.nextLine();
                    System.out.print("Enter department: ");
                    String department = scanner.nextLine();
                    System.out.print("Enter semester (SPRING, SUMMER, or FALL): ");
                    String semesterStr = scanner.nextLine().toUpperCase();
                    try {
                        Semester semester = Semester.valueOf(semesterStr);
                        courseService.addCourse(code, title, credits, instructor, semester, department);
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid semester. Please enter SPRING, SUMMER, or FALL.");
                    }
                    break;
                case 2:
                    System.out.println("\n--- List of All Courses ---");
                    List<Course> courses = courseService.getAllCourses();
                    if (courses.isEmpty()) {
                        System.out.println("No courses found.");
                    } else {
                        for (Course course : courses) {
                            System.out.println(course.toString());
                        }
                    }
                    break;
                case 3:
                    System.out.print("Enter search keyword for course title: ");
                    String query = scanner.nextLine();
                    List<Course> foundCourses = courseService.searchCourses(query);
                    System.out.println("\n--- Search Results ---");
                    if (foundCourses.isEmpty()) {
                        System.out.println("No courses found matching '" + query + "'.");
                    } else {
                        for (Course course : foundCourses) {
                            System.out.println(course.toString());
                        }
                    }
                    break;
                case 4:
                    System.out.println("Returning to Main Menu...");
                    return; // Exit this method to go back
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}