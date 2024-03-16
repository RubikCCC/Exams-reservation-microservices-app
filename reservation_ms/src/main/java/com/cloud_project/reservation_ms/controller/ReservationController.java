package com.cloud_project.reservation_ms.controller;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cloud_project.reservation_ms.dto.ReservationDto;
import com.cloud_project.reservation_ms.entity.Reservation;
import com.cloud_project.reservation_ms.service.ReservationService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/reservations")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/{email}")
    public ResponseEntity<List<ReservationDto>> getReservationsByEmail(@PathVariable String email) {
        List<ReservationDto> reservationDtos = reservationService.getReservationsByEmail(email);
        return ResponseEntity.ok(reservationDtos);
    }

    @GetMapping
    public ResponseEntity<List<Reservation>> getReservationsByCourseNameAndDate(@RequestParam("course_name") String courseName,
                                                                                @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime date) {
        List<Reservation> reservationsList = reservationService.getReservationsByCourseNameAndDate(courseName, date);
        return ResponseEntity.ok(reservationsList);
    }

    @PostMapping
    public ResponseEntity<Reservation> addReservation(@Valid @RequestBody Reservation reservation,
                                                      BindingResult bindingResult) throws URISyntaxException {
        if(bindingResult.hasErrors())
            return ResponseEntity.badRequest().build();
        Reservation newReservation = reservationService.addReservation(reservation);
        return ResponseEntity.ok(newReservation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable("id") Long reservationId) {
        if(reservationService.getReservation(reservationId).isEmpty())
            return ResponseEntity.notFound().build();
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.ok().build();
    }
}