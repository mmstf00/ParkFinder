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
        return Duration.between(dateTimeFrom, dateTimeTo).abs();
    }

    public static String formatDuration(Duration duration) {
        long days = duration.toDays();
        long hours = duration.toHours() % 24;
        long minutes = duration.toMinutes() % 60;

        String durationStr;
        if (days > 0) {
            durationStr = String.format("%d days, %d hours, %d minutes", days, hours, minutes);
        } else if (hours > 0) {
            durationStr = String.format("%d hours, %d minutes", hours, minutes);
        } else {
            durationStr = String.format("%d minutes", minutes);
        }
        return durationStr;
    }
}
