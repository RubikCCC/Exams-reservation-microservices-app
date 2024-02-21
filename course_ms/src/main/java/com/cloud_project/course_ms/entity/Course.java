package com.cloud_project.course_ms.entity;

import java.util.List;

import org.hibernate.validator.constraints.Range;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "courses", uniqueConstraints={@UniqueConstraint(columnNames = {"course_name"})})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", length = 50, nullable = false)
    private String courseName;

    @ElementCollection
    @NotEmpty
    @Size(max = 30)
    private List<String> professors;

    @Column(nullable = false)
    @Range(min = 1, max = 12)
    private Integer cfu;

    @Column(length = 10, nullable = false)
    private String ssd;

    public Course() {
    }

    public Course(Long id, String courseName, List<String> professors, Integer cfu, String ssd) {
        this.id = id;
        this.courseName = courseName;
        this.professors = professors;
        this.cfu = cfu;
        this.ssd = ssd;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<String> getProfessors() {
        return this.professors;
    }

    public void setProfessors(List<String> professors) {
        this.professors = professors;
    }

    public Integer getCfu() {
        return this.cfu;
    }

    public void setCfu(Integer cfu) {
        this.cfu = cfu;
    }

    public String getSsd() {
        return this.ssd;
    }

    public void setSsd(String ssd) {
        this.ssd = ssd;
    }
}