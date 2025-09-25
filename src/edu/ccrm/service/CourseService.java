package edu.ccrm.service;

import edu.ccrm.domain.Course;
import edu.ccrm.domain.Semester;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CourseService {

    private final List<Course> courses = new ArrayList<>();

    /**
     * Adds a new course using the Builder pattern.
     * @return The newly created Course object.
     */
    public Course addCourse(String code, String title, int credits, String instructor, Semester semester, String department) {
        Course newCourse = new Course.Builder(code, title)
            .credits(credits)
            .instructor(instructor)
            .semester(semester)
            .department(department)
            .build();
        
        courses.add(newCourse);
        System.out.println("Course added successfully: " + title);
        return newCourse;
    }

    /**
     * Finds a course by its unique code.
     * @param courseCode The code to search for.
     * @return The Course object if found, or null if not found.
     */
    public Course findCourseByCode(String courseCode) {
        for (Course course : courses) {
            if (course.getCode().equalsIgnoreCase(courseCode)) {
                return course;
            }
        }
        return null;
    }
    
    /**
     * Searches for courses using the Stream API.
     * @param query The search text to look for in the course title.
     * @return A list of courses that match the query.
     */
    public List<Course> searchCourses(String query) {
        return courses.stream()
            .filter(course -> course.getTitle().toLowerCase().contains(query.toLowerCase()))
            .collect(Collectors.toList());
    }

    /**
     * Returns a copy of the list of all courses.
     * @return A list of all courses.
     */
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    /**
     * Deactivates a course by changing its status.
     * @param courseCode The code of the course to deactivate.
     * @return true if the course was found and deactivated, false otherwise.
     */
    public boolean deactivateCourse(String courseCode) {
        Course courseToDeactivate = findCourseByCode(courseCode);
        if (courseToDeactivate != null) {
            courseToDeactivate.setStatus("Inactive");
            return true;
        }
        return false;
    }
}