package com.parkfinder.util;

import com.parkfinder.entity.Reservation;
import com.parkfinder.model.ReservationRequest;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
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

    public static BigDecimal calculateFinalReservationPrice(BigDecimal hourlyPrice, Duration duration) {
        BigDecimal minutes = new BigDecimal(duration.toMinutes());
        BigDecimal hours = minutes.divide(new BigDecimal(60), 2, RoundingMode.HALF_UP);
        return hourlyPrice.multiply(hours);
    }

}
