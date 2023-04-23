package com.parkfinder.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateRequest {
    private Long id;
    private String address;
    private String details;
    private Double price;
}
