package com.cloud_project.result_ms.controller;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

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

import com.cloud_project.result_ms.dto.ResultWithExamDto;
import com.cloud_project.result_ms.entity.Result;
import com.cloud_project.result_ms.service.ResultService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/results")
public class ResultController {
    @Autowired
    private ResultService resultService;

    @GetMapping("/{email}")
    public ResponseEntity<List<ResultWithExamDto>> getResultsByEmail(@PathVariable String email) {
        List<ResultWithExamDto> resultsList = resultService.getResultsByEmail(email);
        return ResponseEntity.ok(resultsList);
    }

    @GetMapping
    public ResponseEntity<ResultWithExamDto> getResultByEmailAndCourseNameAndDate(@RequestParam String email,
                                                            @RequestParam("course_name") String courseName, 
                                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        ResultWithExamDto result = resultService.getResultByEmailAndCourseNameAndDate(email, courseName, date);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<Result> addResult(@Valid @RequestBody Result result,
                                            BindingResult bindingResult) throws URISyntaxException {
        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().build();
        Result newResult = resultService.addResult(result);
        return ResponseEntity.ok(newResult);
    }
}
