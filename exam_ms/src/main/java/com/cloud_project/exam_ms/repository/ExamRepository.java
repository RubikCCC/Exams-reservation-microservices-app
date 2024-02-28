package com.cloud_project.exam_ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cloud_project.exam_ms.entity.Exam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    List<Exam> findByCourseName(String courseName);
    Optional<Exam> findByCourseNameAndDate(String courseName, LocalDateTime date);
}