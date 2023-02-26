package com.parkfinder.controller.rest;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.ReservationModel;
import com.parkfinder.service.MarkerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RestMarkerController {

    private final MarkerService markerService;

    public RestMarkerController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @GetMapping(produces = "application/json")
    public List<Marker> getAllMarkers() {
        return markerService.getMarkers();
    }

    @GetMapping(value = "/getMarker", produces = "application/json")
    public Marker getMarker(@RequestParam("lat") double latitude, @RequestParam("lng") double longitude) {
        return markerService.getMarkerByLatLng(latitude, longitude);
    }

    @GetMapping(value = "/search", produces = "application/json")
    public List<Marker> getAllByDateRange(
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTo) {
        return markerService.getAllBySelectedDateRange(dateFrom, dateTo);
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateMarker(@RequestBody ReservationModel reservation) {
        markerService.updateMarkerReservationById(reservation.getId(), reservation.isNotReserved());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
