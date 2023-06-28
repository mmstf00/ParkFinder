package com.parkfinder.model.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import java.math.BigDecimal;

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
    @NotNull(message = "Price cannot be empty")
    private BigDecimal priceTag;
    @Min(value = 1, message = "Parking should have minimum size 1")
    private int parkSize;
    private ReservationDTO reservationDTO;
    private double latitude;
    private double longitude;
    private String detailedInformation;
}
