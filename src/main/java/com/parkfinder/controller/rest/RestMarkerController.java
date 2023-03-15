package com.parkfinder.controller.rest;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.ReservationModel;
import com.parkfinder.service.MarkerService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class RestMarkerController {

    private final MarkerService markerService;

    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Marker>> getAllMarkers() {
        List<Marker> markers = markerService.getMarkers();
        return new ResponseEntity<>(markers, HttpStatus.OK);
    }

    @GetMapping(value = "/getMarker", produces = "application/json")
    public ResponseEntity<Marker> getMarker(@RequestParam("lat") double latitude,
                                            @RequestParam("lng") double longitude) {
        Marker markerByLatLng = markerService.getMarkerByLatLng(latitude, longitude);
        return new ResponseEntity<>(markerByLatLng, HttpStatus.OK);
    }

    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<List<Marker>> getAllByDateRange(
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTo) {
        List<Marker> allBySelectedDateRange = markerService.getAllBySelectedDateRange(dateFrom, dateTo);
        return new ResponseEntity<>(allBySelectedDateRange, HttpStatus.OK);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateMarker(@RequestBody ReservationModel reservation) {
        markerService.updateMarkerReservationById(reservation.getId(), reservation.isNotReserved());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
