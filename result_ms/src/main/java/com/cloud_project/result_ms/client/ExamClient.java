package com.cloud_project.result_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.cloud_project.result_ms.external.Exam;

@FeignClient(name = "exam-ms")
public interface ExamClient {
    @GetMapping("/exams/{id}")
    Exam getExamById(@PathVariable Long id);
}
