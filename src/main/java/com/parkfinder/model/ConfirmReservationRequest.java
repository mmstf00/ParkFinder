package com.parkfinder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class ConfirmReservationRequest {
    private Long parkingId;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate parkingDateFrom;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime parkingTimeFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate parkingDateTo;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime parkingTimeTo;
}
