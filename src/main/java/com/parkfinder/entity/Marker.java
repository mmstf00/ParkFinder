package com.parkfinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Marker {
    @Id
    @GeneratedValue
    private Long id;
    private String address;
    private double priceTag;
    @Column(unique = true)
    private double latitude;
    @Column(unique = true)
    private double longitude;
}
