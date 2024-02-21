package com.cloud_project.exam_ms.controller;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud_project.exam_ms.entity.Exam;
import com.cloud_project.exam_ms.service.ExamService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/exams")
public class ExamController {
    @Autowired
    private ExamService examService;

    @GetMapping("/{id}")
    public ResponseEntity<Exam> getExam(@PathVariable Long id) {
        Optional<Exam> exam = examService.getExam(id);
        if(exam.isPresent())
            return ResponseEntity.ok(exam.get());
        else
            return ResponseEntity.notFound().build();
    }
    
    @GetMapping
    public ResponseEntity<Exam> getExamByCourseNameAndDate(@RequestParam("course_name") String courseName,
                                        @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        Optional<Exam> exam = examService.getExamByCourseNameAndDate(courseName, date);
        if(exam.isPresent())
            return ResponseEntity.ok(exam.get());
        else
            return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/course")
    public ResponseEntity<List<Exam>> getExamsByCourseName(@RequestParam("course_name") String courseName) {
        List<Exam> examsList = examService.getExamsByCourseName(courseName);
        return ResponseEntity.ok(examsList);
    }

    @PostMapping
    public ResponseEntity<Exam> addExam(@Valid @RequestBody Exam exam,
                                        BindingResult bindingResult) throws URISyntaxException {
        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().build();
        Exam newExam = examService.addExam(exam);
        return ResponseEntity.ok(newExam);
    }
}
