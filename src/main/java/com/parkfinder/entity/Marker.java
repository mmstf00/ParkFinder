package com.parkfinder.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
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
    @OneToMany(mappedBy = "marker", fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private List<Reservation> reservations;
    @Column(unique = true)
    private double latitude;
    @Column(unique = true)
    private double longitude;
    @Column(columnDefinition = "text", length = 490)
    private String detailedInformation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Marker marker = (Marker) o;
        return Double.compare(marker.priceTag, priceTag) == 0 &&
                Double.compare(marker.latitude, latitude) == 0 &&
                Double.compare(marker.longitude, longitude) == 0 &&
                Objects.equals(id, marker.id) &&
                Objects.equals(address, marker.address) &&
                Objects.equals(placeId, marker.placeId) &&
                Objects.equals(reservations, marker.reservations);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, placeId, priceTag,
                reservations, latitude, longitude);
    }
}
