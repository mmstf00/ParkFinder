package com.parkfinder.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

@Getter
@Setter
public class ReservationRequest {
    private Long id;
    private String plateNumber;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateFrom;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeFrom;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dateTo;
    @DateTimeFormat(pattern = "HH:mm:ss")
    private LocalTime timeTo;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationRequest that = (ReservationRequest) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(plateNumber, that.plateNumber) &&
                Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(timeFrom, that.timeFrom) &&
                Objects.equals(dateTo, that.dateTo) &&
                Objects.equals(timeTo, that.timeTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, plateNumber, dateFrom, timeFrom, dateTo, timeTo);
    }
}
