package com.parkfinder.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class EmptyEntity {
    @Id
    @GeneratedValue
    private Long id;
}
