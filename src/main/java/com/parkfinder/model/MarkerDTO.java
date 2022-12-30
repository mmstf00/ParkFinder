package com.parkfinder.model;

import lombok.Data;

@Data
public class MarkerDTO {
    // TODO: Add validation to the attributes.
    private String priceTag;
    private Long latitude;
    private Long longitude;
}
