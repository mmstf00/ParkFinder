package com.parkfinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Marker {
    @Id
    @GeneratedValue
    private Long id;
    private String address;
    private double priceTag;
    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime dateFrom;
    @Column(columnDefinition = "TIMESTAMP(0)")
    private LocalDateTime dateTo;
    @Column(unique = true)
    private double latitude;
    @Column(unique = true)
    private double longitude;
    private boolean isReservable = true;
}
