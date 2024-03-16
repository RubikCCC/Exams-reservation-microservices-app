package com.cloud_project.course_ms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud_project.course_ms.entity.Course;
import com.cloud_project.course_ms.repository.CourseRepository;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    public Course addCourse(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> getCourse(Long id) {
        return courseRepository.findById(id);
    }

    public Optional<Course> getCourseByCourseName(String courseName) {
        return courseRepository.findByCourseName(courseName);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course updateCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }
}
