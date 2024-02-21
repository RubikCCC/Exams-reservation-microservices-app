package com.cloud_project.result_ms.external;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Exam {
    private Long id;

    private String courseName;

    private LocalDateTime date;

    private LocalDate bookableFrom;

    private LocalDate bookableTo;

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