package com.cloud_project.reservation_ms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cloud_project.reservation_ms.client.ExamClient;
import com.cloud_project.reservation_ms.dto.ReservationDto;
import com.cloud_project.reservation_ms.external.Exam;
import com.cloud_project.reservation_ms.mapper.ReservationMapper;
import com.cloud_project.reservation_ms.entity.Reservation;
import com.cloud_project.reservation_ms.repository.ReservationRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ExamClient examClient;

    public Optional<Reservation> getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public Reservation addReservation(Reservation reservation) {
        reservation.setReservationDate(LocalDateTime.now());
        return reservationRepository.save(reservation);
    }

    public List<ReservationDto> getReservationsByEmail(String email) {
        List<Reservation> reservations = reservationRepository.findByEmail(email);
        return reservations.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ReservationDto convertToDto(Reservation reservation) {
        Exam exam = examClient.getExamById(reservation.getExamId());
        ReservationDto reservationDto = ReservationMapper.mapToReservationDto(reservation, exam);
        return reservationDto;
    }

    public List<Reservation> getReservationsByCourseNameAndDate(String courseName, LocalDateTime date) {
        Exam exam = examClient.getExamByCourseNameAndDate(courseName, date.toString());
        return reservationRepository.findAll().stream()
                                              .filter(x -> x.getExamId() == exam.getId())
                                              .collect(Collectors.toList());
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}