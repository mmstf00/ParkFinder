package com.parkfinder.model;

import jakarta.validation.constraints.DecimalMin;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
public class MarkerDTO {
    @Length.List({
        @Length(min = 10, message = "Address must be at least 10 characters"),
        @Length(max = 40, message = "Address must be less than 40 characters")
    })
    private String address;
    private String placeId;
    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private double priceTag;
    private ReservationDTO reservationDTO;
    private double latitude;
    private double longitude;
    private String detailedInformation;
}
