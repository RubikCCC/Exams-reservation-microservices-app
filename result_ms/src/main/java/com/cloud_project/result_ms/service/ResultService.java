package com.cloud_project.result_ms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud_project.result_ms.dto.ResultWithExamDto;
import com.cloud_project.result_ms.entity.Result;
import com.cloud_project.result_ms.external.Exam;
import com.cloud_project.result_ms.repository.ResultRepository;

@Service
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;

    public Result addResult(Result result) {
        return resultRepository.save(result);
    }

    public Optional<Result> getResult(Long resultId) {
        return resultRepository.findById(resultId);
    }

    public List<ResultWithExamDto> getResultsByEmail(String email) {
        List<Result> results = resultRepository.findByEmail(email);
        return results.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ResultWithExamDto getResultByEmailAndCourseNameAndDate(String email, String courseName, LocalDateTime date) {
        List<Result> results = resultRepository.findByEmail(email);
        List<ResultWithExamDto> result = results.stream()
                                                .map(this::convertToDto)
                                                .filter(x -> x.getExam().getCourseName().equals(courseName)
                                                        && x.getExam().getDate().equals(date))
                                                .collect(Collectors.toList());
        if(!result.isEmpty())
            return result.get(0); // the first result is the only one in the list
        else return new ResultWithExamDto();
    }

    private ResultWithExamDto convertToDto(Result result) {
        ResultWithExamDto resultWithExamDto = new ResultWithExamDto();
        resultWithExamDto.setResult(result);
        RestTemplate restTemplate = new RestTemplate();
        Exam exam = restTemplate.getForObject("http://localhost:8082/exams/" +
                                              result.getExamId(),
                                              Exam.class);
        resultWithExamDto.setExam(exam);
        return resultWithExamDto;
    }
}
