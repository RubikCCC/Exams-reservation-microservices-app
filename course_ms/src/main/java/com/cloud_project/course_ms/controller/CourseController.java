package com.cloud_project.course_ms.controller;

import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cloud_project.course_ms.entity.Course;
import com.cloud_project.course_ms.service.CourseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<List<Course>> getAllCourses() {
        List<Course> coursesList = courseService.getAllCourses();
        return ResponseEntity.ok(coursesList);
    }

    @GetMapping("/{course_name}")
    public ResponseEntity<Course> getCourse(@PathVariable("course_name") String courseName) {
        Optional<Course> course = courseService.getCourseByCourseName(courseName);
        if(course.isPresent())
            return ResponseEntity.ok(course.get());
        else
            return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Course> addCourse(@Valid @RequestBody Course course,
                                            BindingResult bindingResult) throws URISyntaxException {
        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().build();
        Course newCourse = courseService.addCourse(course);
        return ResponseEntity.ok(newCourse);
    }

    @PutMapping
    public ResponseEntity<Course> updateCourse(@Valid @RequestBody Course course,
                                               BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().build();
        Course updatedCourse = courseService.updateCourse(course);
        return ResponseEntity.ok(updatedCourse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourse(@PathVariable("id") Long id) {
        if(courseService.getCourse(id).isEmpty())
            return ResponseEntity.notFound().build();
        courseService.deleteCourse(id);
        return ResponseEntity.ok().build();
    }
}