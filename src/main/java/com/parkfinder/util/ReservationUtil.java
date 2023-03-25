package com.parkfinder.util;

import com.parkfinder.entity.Reservation;
import com.parkfinder.model.ReservationRequest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class ReservationUtil {

    private ReservationUtil() {
        throw new UnsupportedOperationException("Cannot create instance of Utility class");
    }

    public static Reservation getReservationFromRequest(ReservationRequest reservationRequest) {
        LocalDate dateFrom = reservationRequest.getDateFrom();
        LocalTime timeFrom = reservationRequest.getTimeFrom();
        LocalDate dateTo = reservationRequest.getDateTo();
        LocalTime timeTo = reservationRequest.getTimeTo();

        Reservation reservation = new Reservation();
        reservation.setPlateNumber(reservationRequest.getPlateNumber());
        reservation.setDateFrom(LocalDateTime.of(dateFrom, timeFrom));
        reservation.setDateTo(LocalDateTime.of(dateTo, timeTo));
        return reservation;
    }

}
