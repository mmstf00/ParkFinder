package com.parkfinder.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode
public class UpdateRequest {
    private Long id;
    private String address;
    private String details;
    private BigDecimal price;
}
