package edu.ccrm.cli;

import edu.ccrm.domain.*;
import edu.ccrm.exception.DuplicateEnrollmentException;
import edu.ccrm.exception.MaxCreditLimitExceededException;
import edu.ccrm.io.BackupService;
import edu.ccrm.io.ImportExportService;
import edu.ccrm.service.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // --- Initialize Services ---
        StudentService studentService = new StudentServiceImpl();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService();
        TranscriptService transcriptService = new TranscriptService();
        ImportExportService importExportService = new ImportExportService();
        BackupService backupService = new BackupService();
        Scanner scanner = new Scanner(System.in);

        // --- Load data at startup ---
        importExportService.importData(studentService, courseService, enrollmentService);

        // --- Main Application Loop ---
        while (true) {
            System.out.println("\n--- Campus Course & Records Manager ---");
            System.out.println("1. Manage Students");
            System.out.println("2. Manage Courses");
            System.out.println("3. Manage Enrollments");
            System.out.println("4. Generate Reports");
            System.out.println("5. File Utilities");
            System.out.println("6. Run Demos");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    manageStudents(scanner, studentService, enrollmentService, transcriptService);
                    break;
                case 2:
                    manageCourses(scanner, courseService);
                    break;
                case 3:
                    manageEnrollments(scanner, studentService, courseService, enrollmentService);
                    break;
                case 4:
                    transcriptService.generateGpaDistributionReport(enrollmentService.getAllEnrollments());
                    break;
                case 5:
                    manageFileUtilities(scanner, backupService);
                    break;
                case 6:
                    runDemos();
                    break;
                case 7:
                    importExportService.exportData(
                        studentService.getAllStudents(),
                        courseService.getAllCourses(),
                        enrollmentService.getAllEnrollments()
                    );
                    System.out.println("Exiting application. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private static void manageStudents(Scanner scanner, StudentService studentService, EnrollmentService enrollmentService, TranscriptService transcriptService) {
        while (true) {
            System.out.println("\n--- Student Management ---");
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Find Student by Registration No.");
            System.out.println("4. Update Student Details");
            System.out.println("5. Deactivate Student");
            System.out.println("6. Print Student Transcript");
            System.out.println("7. Back to Main Menu");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine();
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
                    Student foundStudent = studentService.findStudent(searchRegNo);
                    if (foundStudent != null) {
                        System.out.println("\n--- Student Found ---");
                        foundStudent.printDetails();
                        System.out.println("---------------------");
                    } else {
                        System.out.println("Student with registration number '" + searchRegNo + "' not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter registration number of student to update: ");
                    String regNoToUpdate = scanner.nextLine();
                    System.out.print("Enter new full name: ");
                    String newName = scanner.nextLine();
                    System.out.print("Enter new email: ");
                    String newEmail = scanner.nextLine();
                    boolean updated = studentService.updateStudent(regNoToUpdate, newName, newEmail);
                    if (updated) {
                        System.out.println("Student details updated successfully.");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 5:
                    System.out.print("Enter registration number of student to deactivate: ");
                    String regNoToDeactivate = scanner.nextLine();
                    boolean deactivated = studentService.deactivateStudent(regNoToDeactivate);
                    if (deactivated) {
                        System.out.println("Student deactivated successfully.");
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 6:
                    System.out.print("Enter registration number to print transcript: ");
                    String regNoForTranscript = scanner.nextLine();
                    Student studentForTranscript = studentService.findStudent(regNoForTranscript);
                    if (studentForTranscript != null) {
                        List<Enrollment> studentEnrollments = enrollmentService.getEnrollmentsForStudent(studentForTranscript);
                        transcriptService.printTranscript(studentForTranscript, studentEnrollments);
                    } else {
                        System.out.println("Student not found.");
                    }
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageCourses(Scanner scanner, CourseService courseService) {
         while (true) {
            System.out.println("\n--- Course Management ---");
            System.out.println("1. Add New Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Courses by Title");
            System.out.println("4. Deactivate Course");
            System.out.println("5. Back to Main Menu");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter course code (e.g., CS101): ");
                    String code = scanner.nextLine();
                    System.out.print("Enter course title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter credits: ");
                    int credits = scanner.nextInt();
                    scanner.nextLine();
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
                    System.out.print("Enter course code to deactivate: ");
                    String codeToDeactivate = scanner.nextLine();
                    boolean deactivated = courseService.deactivateCourse(codeToDeactivate);
                    if (deactivated) {
                        System.out.println("Course deactivated successfully.");
                    } else {
                        System.out.println("Course not found.");
                    }
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void manageEnrollments(Scanner scanner, StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        System.out.println("\n--- Enrollment Management ---");
        System.out.println("1. Enroll Student");
        System.out.println("2. Unenroll Student");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                System.out.print("Enter student's registration number: ");
                String regNo = scanner.nextLine();
                Student student = studentService.findStudent(regNo);
                System.out.print("Enter course code: ");
                String courseCode = scanner.nextLine();
                Course course = courseService.findCourseByCode(courseCode);
                if (student != null && course != null) {
                    try {
                        enrollmentService.enrollStudent(student, course);
                    } catch (DuplicateEnrollmentException | MaxCreditLimitExceededException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                } else {
                    System.out.println("Error: Invalid student registration number or course code.");
                }
                break;
            case 2:
                System.out.print("Enter student's registration number: ");
                String regNoToUnenroll = scanner.nextLine();
                Student studentToUnenroll = studentService.findStudent(regNoToUnenroll);
                System.out.print("Enter course code: ");
                String courseCodeToUnenroll = scanner.nextLine();
                Course courseToUnenroll = courseService.findCourseByCode(courseCodeToUnenroll);
                if (studentToUnenroll != null && courseToUnenroll != null) {
                    boolean success = enrollmentService.unenrollStudent(studentToUnenroll, courseToUnenroll);
                    if (success) {
                        System.out.println("Student successfully unenrolled.");
                    } else {
                        System.out.println("Error: Enrollment not found.");
                    }
                } else {
                    System.out.println("Error: Invalid student registration number or course code.");
                }
                break;
            case 3:
                return;
            default:
                System.out.println("Invalid choice.");
        }
    }
    
    private static void recordGrade(Scanner scanner, StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        System.out.print("Enter student's registration number: ");
        String regNoForGrade = scanner.nextLine();
        Student studentForGrade = studentService.findStudent(regNoForGrade);
        System.out.print("Enter course code: ");
        String courseCodeForGrade = scanner.nextLine();
        Course courseForGrade = courseService.findCourseByCode(courseCodeForGrade);
        if (studentForGrade != null && courseForGrade != null) {
            System.out.print("Enter grade (S, A, B, C, D, or F): ");
            String gradeStr = scanner.nextLine().toUpperCase();
            try {
                Grade grade = Grade.valueOf(gradeStr);
                boolean success = enrollmentService.recordGrade(studentForGrade, courseForGrade, grade);
                if (success) {
                    System.out.println("Grade recorded successfully!");
                } else {
                    System.out.println("Error: Could not record grade. Enrollment not found.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: Invalid grade entered. Please use S, A, B, C, D, or F.");
            }
        } else {
            System.out.println("Error: Invalid student registration number or course code.");
        }
    }
    
    private static void manageFileUtilities(Scanner scanner, BackupService backupService) {
        System.out.println("\n--- File Utilities ---");
        System.out.println("1. Create Data Backup");
        System.out.println("2. Show Backups Size (Recursive)");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch(choice) {
            case 1:
                backupService.createBackup();
                break;
            case 2:
                Path backupPath = Paths.get("backups");
                if (Files.exists(backupPath)) {
                    long totalSize = backupService.calculateDirectorySize(backupPath);
                    System.out.println("Total size of all backups: " + totalSize / 1024 + " KB");
                } else {
                    System.out.println("No backup directory found.");
                }
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private static void runDemos() {
        System.out.println("\n--- Running All Demos ---");
        demonstrateArrayFeatures();
        demonstrateCasting();
        demonstrateAnonymousClass();
        System.out.println("\n--- Demos Complete ---");
    }

    private static void demonstrateArrayFeatures() {
        System.out.println("\n--- Demonstrating Array Features ---");
        String[] courseCodes = {"CS101", "MA203", "PH102", "EN101"};
        System.out.println("Original array: " + Arrays.toString(courseCodes));
        Arrays.sort(courseCodes);
        System.out.println("Sorted array:   " + Arrays.toString(courseCodes));
        String searchKey = "PH102";
        int index = Arrays.binarySearch(courseCodes, searchKey);
        if (index >= 0) {
            System.out.println("Found '" + searchKey + "' at index: " + index);
        } else {
            System.out.println("Could not find '" + searchKey + "'.");
        }
        System.out.println("\n--- Demonstrating Loop Jumps ---");
        searchLoop:
        for (String code : courseCodes) {
            if (code.startsWith("MA")) {
                System.out.println("Skipping Math course: " + code);
                continue;
            }
            System.out.println("Processing course: " + code);
            if (code.equals("PH102")) {
                System.out.println("Found the Physics course! Breaking out of the entire loop.");
                break searchLoop;
            }
        }
        System.out.println("------------------------------------");
    }

    private static void demonstrateCasting() {
        System.out.println("\n--- Demonstrating instanceof and Casting ---");
        List<Person> people = new ArrayList<>();
        people.add(new Student("Alice Wonderland", "alice@example.com", "S101"));
        people.add(new Instructor("Bob Builder", "bob@example.com", "EMP45"));
        for (Person p : people) {
            System.out.println("\nProcessing person: " + p.getFullName());
            if (p instanceof Student) {
                System.out.println("This person is a Student.");
                Student s = (Student) p;
                System.out.println("Accessed student-specific data: Reg No " + s.getRegNo());
            } else if (p instanceof Instructor) {
                System.out.println("This person is an Instructor.");
                Instructor i = (Instructor) p;
                System.out.println("Accessed instructor-specific data: Employee ID " + i.getEmployeeId());
            }
        }
        System.out.println("------------------------------------------");
    }
    
    private static void demonstrateAnonymousClass() {
        System.out.println("\n--- Demonstrating Anonymous Inner Class ---");
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("Charlie Brown", "charlie@example.com", "S103"));
        studentList.add(new Student("Alice Wonderland", "alice@example.com", "S101"));
        studentList.add(new Student("Bob Builder", "bob@example.com", "S102"));

        System.out.println("Original list of students: " + studentList);

        studentList.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
                return s1.getFullName().compareTo(s2.getFullName());
            }
        });

        System.out.println("Sorted list of students (by name): " + studentList);
        System.out.println("-------------------------------------------");
    }
}