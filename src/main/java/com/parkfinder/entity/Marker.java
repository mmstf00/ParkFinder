package com.parkfinder.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@Entity
@EqualsAndHashCode
public class Marker {
    @Id
    @GeneratedValue
    private Long id;
    private String address;
    private String placeId;
    private BigDecimal priceTag;
    @OneToMany(mappedBy = "marker", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<Reservation> reservations;
    @Column(unique = true)
    private double latitude;
    @Column(unique = true)
    private double longitude;
    @Column(columnDefinition = "text", length = 490)
    private String detailedInformation;
    private int parkSize;
    private int usedLots;
}
