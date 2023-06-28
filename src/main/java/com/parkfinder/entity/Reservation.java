package com.parkfinder.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
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
    private String totalDuration;
    private BigDecimal totalCost;
    @JsonIgnore
    @ManyToOne
    private User customer;
    @JsonBackReference
    @ManyToOne(cascade = CascadeType.ALL)
    private Marker marker;
    private boolean isExpired;
}
