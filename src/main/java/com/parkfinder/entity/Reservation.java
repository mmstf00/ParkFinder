package com.parkfinder.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime dateFrom;
    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime dateTo;
    private String plateNumber;
    @ManyToOne
    @JsonBackReference
    private Marker marker;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Reservation that = (Reservation) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(dateFrom, that.dateFrom) &&
                Objects.equals(dateTo, that.dateTo) &&
                Objects.equals(marker, that.marker);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, dateFrom, dateTo, marker);
    }
}
