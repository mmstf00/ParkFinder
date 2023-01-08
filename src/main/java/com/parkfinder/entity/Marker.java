package com.parkfinder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
@Entity
@Data
public class Marker {
    @Id
    @GeneratedValue
    private Long id;
    private String address;
    private String priceTag;
    private Long latitude;
    private Long longitude;
}
