package com.parkfinder.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class MarkerDTO {
    @Length(min = 10, max = 40, message = "Address must be with max 40 length")
    private String address;
    private String placeId;
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
    private boolean isReservable = true;
}
