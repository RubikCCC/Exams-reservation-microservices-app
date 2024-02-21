package com.cloud_project.result_ms.dto;

import com.cloud_project.result_ms.external.Exam;

public class ResultDto {
    private Long id;
    
    private String email;

    private Integer mark;

    private Boolean laude;
    
    private Exam exam;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getMark() {
        return this.mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public Boolean isLaude() {
        return this.laude;
    }

    public Boolean getLaude() {
        return this.laude;
    }

    public void setLaude(Boolean laude) {
        this.laude = laude;
    }

    public Exam getExam() {
        return this.exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
