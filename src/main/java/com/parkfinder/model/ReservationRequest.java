package com.parkfinder.model;

import com.parkfinder.entity.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@EqualsAndHashCode
public class ReservationRequest {
    private Long id;
    private String plateNumber;
    private String totalDuration;
    private BigDecimal totalCost;
    private User customer;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeTo;
}
