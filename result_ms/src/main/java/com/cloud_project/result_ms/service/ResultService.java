package com.cloud_project.result_ms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud_project.result_ms.client.ExamClient;
import com.cloud_project.result_ms.dto.ResultDto;
import com.cloud_project.result_ms.entity.Result;
import com.cloud_project.result_ms.external.Exam;
import com.cloud_project.result_ms.mapper.ResultMapper;
import com.cloud_project.result_ms.repository.ResultRepository;

@Service
public class ResultService {
    @Autowired
    private ResultRepository resultRepository;

    @Autowired
    private ExamClient examClient;

    public Result addResult(Result result) {
        return resultRepository.save(result);
    }

    public Optional<Result> getResult(Long resultId) {
        return resultRepository.findById(resultId);
    }

    public List<ResultDto> getResultsByEmail(String email) {
        List<Result> results = resultRepository.findByEmail(email);
        return results.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public ResultDto getResultByEmailAndCourseNameAndDate(String email, String courseName, LocalDateTime date) {
        List<Result> results = resultRepository.findByEmail(email);
        List<ResultDto> resultDtos = results.stream()
                                            .map(this::convertToDto)
                                            .filter(x -> x.getExam().getCourseName().equals(courseName)
                                                    && x.getExam().getDate().equals(date))
                                            .collect(Collectors.toList());
        if(!resultDtos.isEmpty())
            return resultDtos.get(0); // the first result is the only one in the list
        else return new ResultDto();
    }

    private ResultDto convertToDto(Result result) {
        Exam exam = examClient.getExamById(result.getExamId());
        ResultDto resultDto = ResultMapper.mapToResultDto(result, exam);
        return resultDto;
    }
}
