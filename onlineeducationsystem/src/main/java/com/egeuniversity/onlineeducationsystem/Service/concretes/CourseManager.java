package com.egeuniversity.onlineeducationsystem.Service.concretes;

import com.egeuniversity.onlineeducationsystem.Service.abstracts.CourseService;
import com.egeuniversity.onlineeducationsystem.data.Course;
import com.egeuniversity.onlineeducationsystem.dto.CourseSearchDTO;
import com.egeuniversity.onlineeducationsystem.repository.CourseDal;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.egeuniversity.onlineeducationsystem.utility.Utility.getNow;

@Service
public class CourseManager implements CourseService {

    @Autowired
    private CourseDal courseDal;

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
            Optional<Course> optionalCourse = courseDal.findById(id);
            if (optionalCourse.isPresent()) {
                Course course = optionalCourse.get();
                course.setTitle(courseDetails.getTitle());
                course.setDescription(courseDetails.getDescription());
                course.setAttachment(courseDetails.getAttachment());
                course.setCategory(courseDetails.getCategory());
                course.setPrice(courseDetails.getPrice());
                course.setUpdatedAt(getNow());

                return courseDal.save(course);
            } else {
                throw new RuntimeException("Course not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to update course: " + e.getMessage(), e);
        }
    }

    @Override
    public Course getCourseById(String id) {
        try {
            return courseDal.findById(id).orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Failed to retrieve course: " + e.getMessage(), e);
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
            throw new RuntimeException("Error getting courses.");
        }

        return coursePage;
    }

    @Override
    public void deleteCourse(String id) {
        try {
            if (courseDal.existsById(id)) {
                courseDal.deleteById(id);
            } else {
                throw new RuntimeException("Course not found with id: " + id);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete course: " + e.getMessage(), e);
        }
    }

    private void validatePageAndSize(int page, int size) {
        if (page < 1 || size < 1) {
            throw new IllegalArgumentException("Invalid page or size parameter");
        }
    }
}
