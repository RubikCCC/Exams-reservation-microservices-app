package com.cloud_project.exam_ms.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;

@Entity
@Table(name = "exams", uniqueConstraints={@UniqueConstraint(columnNames = {"course_name", "date"})})
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_name", length = 50, nullable = false)
    private String courseName;

    @Column(nullable = false)
    private LocalDateTime date;

    @Column(name = "bookable_from", nullable = false)
    private LocalDate bookableFrom;

    @Column(name = "bookable_to", nullable = false)
    private LocalDate bookableTo;

    public Exam() {
    }

    public Exam(Long id, String courseName, LocalDateTime date, LocalDate bookableFrom, LocalDate bookableTo) {
        this.id = id;
        this.courseName = courseName;
        this.date = date;
        this.bookableFrom = bookableFrom;
        this.bookableTo = bookableTo;
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

    public LocalDateTime getDate() {
        return this.date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public LocalDate getBookableFrom() {
        return this.bookableFrom;
    }

    public void setBookableFrom(LocalDate bookableFrom) {
        this.bookableFrom = bookableFrom;
    }

    public LocalDate getBookableTo() {
        return this.bookableTo;
    }

    public void setBookableTo(LocalDate bookableTo) {
        this.bookableTo = bookableTo;
    }
}