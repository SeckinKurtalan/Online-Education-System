package com.egeuniversity.onlineeducationsystem.Service.abstracts;

import com.egeuniversity.onlineeducationsystem.data.Course;
import com.egeuniversity.onlineeducationsystem.dto.CourseSearchDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;

public interface CourseService {
    Course addCourse(Course course); // Add a new course
    Course updateCourse(String id, Course courseDetails);
    Course getCourseById(String id);
    Page<Course> getAllCourses(CourseSearchDTO dto) throws Exception;
    void deleteCourse(String id);

}
