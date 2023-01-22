package com.parkfinder.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class MarkerDTO {
    @Max(value = 40, message = "Address must be with max 40 length")
    private String address;
    @DecimalMin(value = "0.1", message = "Price must be greater than 0")
    private double priceTag;
    @NotNull(message = "Please select date!")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateFrom;
    @NotNull(message = "Please select date!")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateTo;
    private double latitude;
    private double longitude;
}
