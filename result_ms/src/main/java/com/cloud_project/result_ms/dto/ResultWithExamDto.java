package com.cloud_project.result_ms.dto;

import com.cloud_project.result_ms.entity.Result;
import com.cloud_project.result_ms.external.Exam;

public class ResultWithExamDto {
    private Result result;
    
    private Exam exam;

    public Result getResult() {
        return this.result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public Exam getExam() {
        return this.exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
