package com.cloud_project.exam_ms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud_project.exam_ms.entity.Exam;
import com.cloud_project.exam_ms.repository.ExamRepository;

@Service
public class ExamService {
    @Autowired
    private ExamRepository examRepository;

    public Exam addExam(Exam exam) {
        return examRepository.save(exam);
    }

    public Optional<Exam> getExam(Long examId) {
        return examRepository.findById(examId);
    }

    public List<Exam> getExamsByCourseName(String courseName) {
        return examRepository.findByCourseName(courseName);
    }

    public Optional<Exam> getExamByCourseNameAndDate(String courseName, LocalDateTime date) {
        return examRepository.findByCourseNameAndDate(courseName, date);
    }
}