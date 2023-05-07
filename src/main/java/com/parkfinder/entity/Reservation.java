package com.parkfinder.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Reservation {
    @Id
    @GeneratedValue
    private Long id;
    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime dateFrom;
    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime dateTo;
    private String plateNumber;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    private Marker marker;
}
