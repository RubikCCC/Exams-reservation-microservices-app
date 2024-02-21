package com.cloud_project.reservation_ms.dto;

import com.cloud_project.reservation_ms.external.Exam;
import com.cloud_project.reservation_ms.entity.Reservation;

public class ReservationWithExamDto {
    private Reservation reservation;
    
    private Exam exam;

    public Reservation getReservation() {
        return this.reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Exam getExam() {
        return this.exam;
    }

    public void setExam(Exam exam) {
        this.exam = exam;
    }
}
