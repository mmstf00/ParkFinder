package com.parkfinder.model;

import lombok.Data;

@Data
public class MarkerDTO {
    private String priceTag;
    private Long latitude;
    private Long longitude;
}
