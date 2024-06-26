package com.egeuniversity.onlineeducationsystem.Service.concretes;

import com.egeuniversity.onlineeducationsystem.Exception.ErrorCodes;
import com.egeuniversity.onlineeducationsystem.Exception.GenericException;
import com.egeuniversity.onlineeducationsystem.Service.abstracts.CourseService;
import com.egeuniversity.onlineeducationsystem.data.Course;
import com.egeuniversity.onlineeducationsystem.data.User;
import com.egeuniversity.onlineeducationsystem.dto.CourseSearchDTO;
import com.egeuniversity.onlineeducationsystem.repository.UserDal;
import com.egeuniversity.onlineeducationsystem.utility.Utility;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.egeuniversity.onlineeducationsystem.repository.CourseDal;

import static com.egeuniversity.onlineeducationsystem.utility.Utility.getNow;


@Service
public class CourseManager implements CourseService {

    @Autowired
    private CourseDal courseDal;

    @Autowired
    private UserDal userDal;

    @Override
    public Course addCourse(Course course) {
        try {
            return courseDal.save(course);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save course: " + e.getMessage(), e);
        }
    }

    @Override
    public Course updateCourse(String id, Course courseDetails) {
        try {
            checkIfTheOperationIsAllowed(id);
            Optional<Course> optionalCourse = courseDal.findById(id);
            if (optionalCourse.isPresent()) {
                Course course = optionalCourse.get();

                if (courseDetails.getTitle() != null) {
                    course.setTitle(courseDetails.getTitle());
                }
                if (courseDetails.getDescription() != null) {
                    course.setDescription(courseDetails.getDescription());
                }
                if (courseDetails.getAttachment() != null) {
                    course.setAttachment(courseDetails.getAttachment());
                }
                if (courseDetails.getCategory() != null) {
                    course.setCategory(courseDetails.getCategory());
                }
                if (courseDetails.getPrice() != null) {
                    course.setPrice(courseDetails.getPrice());
                }

                course.setUpdatedAt(getNow());

                return courseDal.save(course);
            } else {
                throw new GenericException(
                        String.format(ErrorCodes.E19_MESSAGE, id), ErrorCodes.E19_CODE, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            throw new GenericException(ErrorCodes.E20_MESSAGE, ErrorCodes.E20_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Course getCourseById(String id) {
        try {
            return courseDal.findById(id).orElseThrow(() -> new GenericException(
                    String.format(ErrorCodes.E19_MESSAGE, id), ErrorCodes.E19_CODE, HttpStatus.NOT_FOUND));
        } catch (Exception e) {
            throw new GenericException(ErrorCodes.E18_MESSAGE, ErrorCodes.E18_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public Page<Course> getAllCourses(CourseSearchDTO dto) throws Exception {
        validatePageAndSize(dto.getPage(), dto.getSize());

        Specification<Course> spec = (root, query, criteriaBuilder) -> {
            List<Predicate> searchPredicates = new ArrayList<>();

            if (dto.getTitle() != null) {
                searchPredicates.add(criteriaBuilder.like(root.get("title"), "%" + dto.getTitle() + "%"));
            }
            if (dto.getDescription() != null) {
                searchPredicates.add(criteriaBuilder.like(root.get("description"), "%" + dto.getDescription() + "%"));
            }
            if (dto.getCategory() != null) {
                searchPredicates.add(criteriaBuilder.like(root.get("category"), "%" + dto.getCategory() + "%"));
            }
            if (dto.getPrice() != null) {
                searchPredicates.add(criteriaBuilder.equal(root.get("price"), dto.getPrice()));
            }
            if (dto.getCreatorId() != null) {
                searchPredicates.add(criteriaBuilder.equal(root.get("creator").get("id"), dto.getCreatorId()));
            }

            return criteriaBuilder.and(searchPredicates.toArray(new Predicate[0]));
        };

        PageRequest pageRequest = PageRequest.of(dto.getPage() - 1, dto.getSize());
        Page<Course> coursePage;

        try {
            coursePage = courseDal.findAll(spec, pageRequest);
        } catch (Exception e) {
            throw new GenericException(ErrorCodes.E16_MESSAGE, ErrorCodes.E16_CODE, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return coursePage;
    }

    @Override
    public void deleteCourse(String id) {
        try {
            checkIfTheOperationIsAllowed(id);
            if (courseDal.existsById(id)) {
                courseDal.deleteById(id);
            } else {
                throw new RuntimeException("Course not found with id: " + id);
            }
        } catch (GenericException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete course: " + e.getMessage(), e);
        }
    }

    @Override
    public Course addUserToCourse(String courseId, Long userId) {
        try {
            Optional<Course> optionalCourse = courseDal.findById(courseId);
            if (optionalCourse.isEmpty()) {
                throw new RuntimeException("Course not found with id: " + courseId);
            }

            Course course = optionalCourse.get();
            if (!userExists(userId)) {
                throw new RuntimeException("User not found with id: " + userId);
            }

            Optional<User> optionalUser = userDal.findById(userId);
            if (optionalUser.isEmpty()) {
                throw new RuntimeException("User not found with id: " + userId);
            }

            User user = optionalUser.get();
            course.getStudents().add(user);

            return courseDal.save(course);
        } catch (Exception e) {
            throw new RuntimeException("Failed to add user to course: " + e.getMessage(), e);
        }
    }

    private void checkIfTheOperationIsAllowed(String courseId) {
        if (courseDal.findById(courseId).isPresent()) {
            Long courseOwner = courseDal.findById(courseId).get().getCreator().getId();
            Long operationOwner = Utility.getUserIdFromToken();
            if (!courseOwner.equals(operationOwner)) {
                throw new GenericException(ErrorCodes.E17_MESSAGE, ErrorCodes.E17_CODE, HttpStatus.UNAUTHORIZED);
            }
        }
    }

    public boolean userExists(Long userId) {
        return userDal.existsById(userId);
    }

    private void validatePageAndSize(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Invalid page or size parameter");
        }
    }
}
