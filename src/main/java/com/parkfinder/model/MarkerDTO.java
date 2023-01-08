package com.parkfinder.model;

import lombok.Data;

@Data
public class MarkerDTO {
    private String address;
    private String priceTag;
    private Long latitude;
    private Long longitude;
}
