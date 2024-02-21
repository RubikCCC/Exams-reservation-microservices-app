package com.cloud_project.result_ms.mapper;

import com.cloud_project.result_ms.dto.ResultDto;
import com.cloud_project.result_ms.entity.Result;
import com.cloud_project.result_ms.external.Exam;

public class ResultMapper {
    public static ResultDto mapToResultDto(
        Result result,
        Exam exam) {
            ResultDto resultDto = new ResultDto();
            resultDto.setId(result.getId());
            resultDto.setEmail(result.getEmail());
            resultDto.setMark(result.getMark());
            resultDto.setLaude(result.getLaude());
            resultDto.setExam(exam);

            return resultDto;
    }
}

