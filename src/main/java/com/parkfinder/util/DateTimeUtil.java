package com.parkfinder.util;

import com.parkfinder.model.ConfirmReservationRequest;

import java.time.Duration;
import java.time.LocalDateTime;

public class DateTimeUtil {

    private DateTimeUtil() {
        throw new UnsupportedOperationException("Cannot create instance of utility class");
    }

    public static Duration getParkingDurationFromRequest(ConfirmReservationRequest request) {
        LocalDateTime dateTimeFrom = LocalDateTime.of(request.getParkingDateFrom(), request.getParkingTimeFrom());
        LocalDateTime dateTimeTo = LocalDateTime.of(request.getParkingDateTo(), request.getParkingTimeTo());
        return Duration.between(dateTimeTo.toLocalTime(), dateTimeFrom.toLocalTime()).abs();
    }
}
