package com.cloud_project.result_ms.entity;

import org.hibernate.annotations.Check;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "results", uniqueConstraints={@UniqueConstraint(columnNames = {"email", "exam_id"})})
@Check(constraints = "((laude = 1 and mark = 30) or (laude = 0 and mark = 30) or (laude = 0 and mark < 30))")
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Email(regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Column(nullable = false)
    private String email;

    @Column(name = "exam_id", nullable = false)
    private Long examId;

    @Column(columnDefinition = "int not null check(mark >= 0 and mark <= 30)")
    private Integer mark;

    @Column(columnDefinition = "tinyint(1) not null")
    private Boolean laude;

    public Result() {
    }

    public Result(Long id, String email, Long examId, Integer mark, Boolean laude) {
        this.id = id;
        this.email = email;
        this.examId = examId;
        this.mark = mark;
        this.laude = laude;
    }

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

    public Long getExamId() {
        return this.examId;
    }

    public void setExamId(Long examId) {
        this.examId = examId;
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
}
