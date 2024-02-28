package com.cloud_project.reservation_ms.mapper;

import com.cloud_project.reservation_ms.dto.ReservationDto;
import com.cloud_project.reservation_ms.entity.Reservation;
import com.cloud_project.reservation_ms.external.Exam;

public class ReservationMapper {
    public static ReservationDto mapToReservationDto(
        Reservation reservation,
        Exam exam) {
            ReservationDto reservationDto = new ReservationDto();
            reservationDto.setId(reservation.getId());
            reservationDto.setEmail(reservation.getEmail());
            reservationDto.setReservationDate(reservation.getReservationDate());
            reservationDto.setExam(exam);

            return reservationDto;
    }
}
