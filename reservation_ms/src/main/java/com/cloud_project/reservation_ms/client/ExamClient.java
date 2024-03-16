package com.cloud_project.reservation_ms.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.cloud_project.reservation_ms.external.Exam;

@FeignClient(name = "exam-ms", url = "exam-ms:8082")
public interface ExamClient {
    @GetMapping("/exams/{id}")
    Exam getExamById(@PathVariable Long id);

    @GetMapping("/exams")
    Exam getExamByCourseNameAndDate(@RequestParam("course_name") String courseName,
                                    @RequestParam("date") String date);
}
