package com.parkfinder.service;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.ReservationRequest;
import com.parkfinder.model.dto.MarkerDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ExtendedMarkerService extends MarkerService {
    Marker getMarkerByLatLng(double latitude, double longitude);

    Marker getMarkerById(Long id);

    void makeReservation(ReservationRequest reservationRequest);

    List<Marker> getAllBySelectedDateRange(LocalDateTime dateFrom, LocalDateTime dateTo);

    boolean isDuplicateEntry(MarkerDTO marker);
}
