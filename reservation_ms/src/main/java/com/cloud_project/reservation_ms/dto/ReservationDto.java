package com.cloud_project.reservation_ms.dto;

import com.cloud_project.reservation_ms.external.Exam;

import java.time.LocalDateTime;

public class ReservationDto {
    private Long id;

    private String email;

    private LocalDateTime reservationDate;
    
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

    public LocalDateTime getReservationDate() {
        return this.reservationDate;
    }

    public void setReservationDate(LocalDateTime reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Exam getExam() {
        return this.exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
