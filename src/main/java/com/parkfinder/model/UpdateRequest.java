package com.parkfinder.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class UpdateRequest {
    private Long id;
    private String address;
    private String details;
    private Double price;
}
