package com.egeuniversity.onlineeducationsystem.Controller;

import com.egeuniversity.onlineeducationsystem.Exception.ErrorCodes;
import com.egeuniversity.onlineeducationsystem.Exception.GenericException;
import com.egeuniversity.onlineeducationsystem.Service.abstracts.CourseService;
import com.egeuniversity.onlineeducationsystem.data.Course;
import com.egeuniversity.onlineeducationsystem.dto.CourseDTO;
import com.egeuniversity.onlineeducationsystem.repository.UserDal;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseService courseService;
    private final UserDal userDal;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
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


    private Course convertDtoToCourse(CourseDTO dto) {
        Course course = new Course();
        course.setTitle(dto.getTitle());
        course.setDescription(dto.getDescription());
        course.setAttachment(dto.getAttachment());
        course.setCategory(dto.getCategory());
        course.setPrice(dto.getPrice());

        if (userDal.findById(dto.getCreatorId()).isEmpty()) {
            throw new GenericException(String.format(ErrorCodes.E11_MESSAGE, dto.getCreatorId()), ErrorCodes.E11_CODE,
                    HttpStatus.NOT_FOUND);
        }

        course.setCreator(userDal.findById(dto.getCreatorId()).get());

        return course;
    }

}
