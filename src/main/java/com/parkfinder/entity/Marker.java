package com.parkfinder.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marker marker = (Marker) o;
        return Double.compare(marker.priceTag, priceTag) == 0 &&
                Double.compare(marker.latitude, latitude) == 0 &&
                Double.compare(marker.longitude, longitude) == 0 &&
                isReservable == marker.isReservable &&
                Objects.equals(id, marker.id) &&
                Objects.equals(address, marker.address) &&
                Objects.equals(placeId, marker.placeId) &&
                Objects.equals(dateFrom, marker.dateFrom) &&
                Objects.equals(dateTo, marker.dateTo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, placeId, priceTag,
                dateFrom, dateTo, latitude, longitude, isReservable);
    }
}
