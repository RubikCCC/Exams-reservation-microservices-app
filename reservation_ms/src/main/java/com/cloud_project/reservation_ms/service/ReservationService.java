package com.cloud_project.reservation_ms.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.cloud_project.reservation_ms.dto.ReservationWithExamDto;
import com.cloud_project.reservation_ms.external.Exam;
import com.cloud_project.reservation_ms.entity.Reservation;
import com.cloud_project.reservation_ms.repository.ReservationRepository;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public Optional<Reservation> getReservation(Long reservationId) {
        return reservationRepository.findById(reservationId);
    }

    public Reservation addReservation(Reservation reservation) {
        reservation.setReservationDate(LocalDateTime.now());
        return reservationRepository.save(reservation);
    }

    public List<ReservationWithExamDto> getReservationsByEmail(String email) {
        List<Reservation> reservations = reservationRepository.findByEmail(email);
        return reservations.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private ReservationWithExamDto convertToDto(Reservation reservation) {
        ReservationWithExamDto reservationWithExamDto = new ReservationWithExamDto();
        reservationWithExamDto.setReservation(reservation);
        RestTemplate restTemplate = new RestTemplate();
        Exam exam = restTemplate.getForObject("http://localhost:8082/exams/" + 
                                              reservation.getExamId(), Exam.class);
        reservationWithExamDto.setExam(exam);
        return reservationWithExamDto;
    }

    public List<Reservation> getReservationsByCourseNameAndDate(String courseName, LocalDateTime date) {
        RestTemplate restTemplate = new RestTemplate();
        Exam exam = restTemplate.getForObject("http://localhost:8082/exams?course_name=" +
                                              courseName + "&date=" + date.toString(),
                                              Exam.class);

        return reservationRepository.findAll().stream()
                                              .filter(x -> x.getExamId() == exam.getId())
                                              .collect(Collectors.toList());
    }

    public void deleteReservation(Long reservationId) {
        reservationRepository.deleteById(reservationId);
    }
}