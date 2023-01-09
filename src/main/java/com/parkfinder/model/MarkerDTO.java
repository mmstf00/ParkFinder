package com.parkfinder.model;

import lombok.Data;

@Data
public class MarkerDTO {
    private String address;
    private double priceTag;
    private double latitude;
    private double longitude;
}
