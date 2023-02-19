package com.parkfinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class Marker {
    @Id
    @GeneratedValue
    private Long id;
    private String address;
    private String placeId;
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
