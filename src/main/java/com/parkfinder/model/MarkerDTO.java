package com.parkfinder.model;

import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

@Data
public class MarkerDTO {
    private String address;
    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private double priceTag;
    private double latitude;
    private double longitude;
}
