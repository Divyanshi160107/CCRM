# CCRM - Campus Course & Records Manager

## Project Overview
This project is the Campus Course & Records Manager (CCRM), a console-based Java application designed to handle the core administrative tasks for a small educational institute. The program allows a user to manage the complete lifecycle of students and courses through a command-line interface.

**Key features include**:
1. Student Management: Creating, updating, listing, and deactivating student profiles.
2. Course Management: Creating, updating, and searching for available courses.
3. Enrollment & Grading: Enrolling students in courses, recording grades, and calculating GPA.
4. Transcripts: Generating a full academic transcript for any student on demand.
5. Data Persistence: Saving all data (students, courses, enrollments) to local files so that no information is lost between          sessions.
6. Backup Utility: Creating timestamped backups of all application data for archival purposes.

---

## How to Run
1.  **Prerequisites**:
* Java Development Kit (JDK): You must have JDK 24 (or a compatible version) installed and configured on your system.
* Git: You must have Git installed to clone the repository from GitHub.
* IDE (Recommended): An Integrated Development Environment like Eclipse or IntelliJ IDEA is recommended for easy   compilation and execution.

2.  **Clone the repository**: 
Open a terminal or Git Bash and clone the repository using the following command 
`git clone <https://github.com/Divyanshi160107/CCRM.git>`

3. **Open in an IDE**: 
* Open Eclipse (or your preferred IDE).
* Import the cloned project folder (File > Import > General > Existing Projects into Workspace).

4. **Run the Application**: 
* In the Package Explorer, navigate to the Main.java file located inside the src/edu/ccrm/cli package.
* Right-click on Main.java and select Run As > Java Application.
* The program will start, and the main menu will be displayed in the console.

---

## Required Documentation

1. **History of Java's Evolution**:

* 1995: Java 1.0 is released by Sun Microsystems, introducing the "Write Once, Run Anywhere" philosophy.
* 2004: Java 5 (J2SE 5.0) is a major release that adds fundamental features to the language, including Generics,    Annotations, Enums, and the enhanced for loop.
* 2014: Java 8 is a revolutionary release, introducing Lambda expressions, the Stream API, and a new Date/Time API, which are all used in modern Java development.
* 2018: Java 11 becomes the first Long-Term Support (LTS) release under Java's new six-month release cadence, establishing a new standard for enterprise applications.



2. **Java ME vs SE vs EE**:

| Feature           | Java ME (Micro Edition)     | Java SE (Standard Edition)      | Java EE (Enterprise Edition)       |
| ----------------- | --------------------------- | ------------------------------- | ---------------------------------- |
| **Target**        | Mobile, embedded devices    | Desktop & server applications   | Large-scale enterprise applications|
| **Core API**      | Subset of SE API            | Core Java language & libraries  | Extends SE with web services, etc. |
| **Example Use**   | Old mobile games,smart cards| This CCRM project, desktop apps | Banking apps, e-commerce sites     |

3. **JDK vs JRE vs JVM**:
* **JVM (Java Virtual Machine)**: The JVM is the "engine" that actually runs compiled Java bytecode. It's what allows a single Java program to run on different operating systems (like Windows, macOS, and Linux) without being changed.

* **JRE (Java Runtime Environment)**: The JRE is the package that provides everything needed to run a Java application. It includes the JVM plus the core Java libraries. A regular user who just wants to run a Java program only needs the JRE.

* **JDK (Java Development Kit)**: The JDK is the full package for developers. It includes everything in the JRE, plus the tools needed to write and build Java applications, such as the compiler (javac) and the debugger.

---

## Mapping Table: Requirements to Code

This table shows where each required technical concept is demonstrated in the code.
| **Syllabus Topic**    | **File / Class**              | **Method / Line Number**                                               |
| --------------------- | ----------------------------- | ---------------------------------------------------------------------  |
| **Inheritance**       | ` Student.java`            | `public class Student extends Person`                           |
| **Abstraction**       | `Person.java`          |`public abstract class Person` with `abstract void printDetails()`|
| **Encapsulation**     | `Student.java`              |		`private String regNo;` with public getters/setters          |
| **Polymorphism**      | `TranscriptService.java`  | `printTranscript()` calls `student.printDetails()`)            |
| **Singleton Pattern** | `AppConfig.java`           | `getInstance()` method                                              |
| **Builder Pattern**   | `Course.java`               | `Course.Builder` static nested class                                |
| **Custom Exception**  | `EnrollmentService.java`  | `enrollStudent()` throws `MaxCreditLimitExceededException`    |
| **Lambda & Streams**  | `CourseService.java`       | `searchCourses()` method uses a stream pipeline                    |
| **NIO.2 File I/O**    | `ImportExportService.java`| `exportData()` uses `Files.newBufferedWriter`                  |
| **Recursion**         | `BackupService.java`       | `calculateDirectorySize()` method                                 |
| **Enums with Fields** | `Grade.java`                 | `Grade(double points)` constructor                                |
| **Date/Time API**     | `Student.java`               | `private LocalDate enrollmentDate;`                            |

---

## Note on Enabling Assertions
This project uses assert statements in the service layer to check for programming errors, such as ensuring method parameters are not null. Assertions are disabled by default in Java and must be explicitly enabled to have any effect.
To run this project with assertions enabled, you need to configure the JVM arguments in your IDE.

*In Eclipse:*
*Go to Run > Run Configurations....
*Select the run configuration for this project (e.g., "Main - CCRM").
*Click on the "Arguments" tab.
*In the "VM arguments" text box, type:
`-ea`
*Click "Apply" and then "Run".