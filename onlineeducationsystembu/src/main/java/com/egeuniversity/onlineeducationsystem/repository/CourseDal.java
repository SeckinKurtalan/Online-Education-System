package com.egeuniversity.onlineeducationsystem.repository;

import com.egeuniversity.onlineeducationsystem.data.Course;
import com.egeuniversity.onlineeducationsystem.data.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CourseDal extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {


}
