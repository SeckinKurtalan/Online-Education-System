package com.egeuniversity.onlineeducationsystem.Controller;

import com.egeuniversity.onlineeducationsystem.utility.Utility;
import com.egeuniversity.onlineeducationsystem.Exception.ErrorCodes;
import com.egeuniversity.onlineeducationsystem.Exception.GenericException;
import com.egeuniversity.onlineeducationsystem.Service.abstracts.CourseService;
import com.egeuniversity.onlineeducationsystem.data.Course;
import com.egeuniversity.onlineeducationsystem.dto.CourseDTO;
import com.egeuniversity.onlineeducationsystem.dto.CourseSearchDTO;
import com.egeuniversity.onlineeducationsystem.repository.UserDal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Course Service")
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserDal userDal;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    public CourseController(CourseService courseService, UserDal userDal) {
        this.courseService = courseService;
        this.userDal = userDal;
    }

    @PostMapping()
    @Operation(summary = "Add Course")
    public ResponseEntity<Course> addCourse(@RequestBody @Valid CourseDTO dto, HttpServletRequest request) {
        try {
            Course savedCourse = courseService.addCourse(convertDtoToCourse(dto));
            return ResponseEntity.ok(savedCourse);
        } catch (Exception e) {
            logger.error("Error adding course", e);
            throw new GenericException(ErrorCodes.E12_MESSAGE, ErrorCodes.E12_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{courseId}/add-user")
    @Operation(summary = "Add User to Course")
    public ResponseEntity<Course> addUserToCourse(@PathVariable String courseId) {
        try {
            Course updatedCourse = courseService.addUserToCourse(courseId, Utility.getUserIdFromToken());
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Course By Id")
    public ResponseEntity<Course> getCourseById(@PathVariable String id) {
        try {
            Course course = courseService.getCourseById(id);
            return ResponseEntity.ok(course);
        } catch (Exception e) {
            logger.error("Error getting course by id", e);
            throw new GenericException(ErrorCodes.E13_MESSAGE, ErrorCodes.E13_CODE, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update Course By Id")
    public ResponseEntity<Course> updateCourse(@PathVariable String id, @RequestBody @Valid CourseDTO dto) {
        try {
            Course updatedCourse = courseService.updateCourse(id, convertDtoToCourse(dto));
            return ResponseEntity.ok(updatedCourse);
        } catch (Exception e) {
            logger.error("Error updating course", e);
            throw new GenericException(ErrorCodes.E14_MESSAGE, ErrorCodes.E14_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Course By Id")
    public ResponseEntity<String> deleteCourse(@PathVariable String id) {
        try {
            courseService.deleteCourse(id);
            return ResponseEntity.ok("Course deleted successfully");
        } catch (GenericException ge) {
            throw ge;
        } catch (Exception e) {
            logger.error("Error deleting course", e);
            throw new GenericException(ErrorCodes.E15_MESSAGE, ErrorCodes.E15_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/search")
    @Operation(summary = "Search Courses")
    public ResponseEntity<List<Course>> searchCourses(@RequestBody CourseSearchDTO dto) {
        try {
            Page<Course> courses = courseService.getAllCourses(dto);
            return ResponseEntity.ok(courses.getContent());
        } catch (Exception e) {
            logger.error("Error searching courses", e);
            throw new GenericException(ErrorCodes.E16_MESSAGE, ErrorCodes.E16_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private Course convertDtoToCourse(CourseDTO dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setAttachment(dto.getAttachment());
        course.setCategory(dto.getCategory());
        course.setPrice(dto.getPrice());

        if (userDal.findById(Utility.getUserIdFromToken()).isEmpty()) {
            throw new GenericException(String.format(ErrorCodes.E11_MESSAGE, Utility.getUserIdFromToken()),
                    ErrorCodes.E11_CODE,
                    HttpStatus.NOT_FOUND);
        }

        course.setCreator(userDal.findById(Utility.getUserIdFromToken()).get());

        return course;
    }
}
