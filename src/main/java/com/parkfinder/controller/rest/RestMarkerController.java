package com.parkfinder.controller.rest;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.ReservationRequest;
import com.parkfinder.model.UpdateRequest;
import com.parkfinder.service.ExtendedMarkerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Parkfinder REST API")
public class RestMarkerController {

    private final ExtendedMarkerService markerService;

    @Operation(summary = "Returns all park locations")
    @GetMapping(produces = "application/json")
    public ResponseEntity<List<Marker>> getAllMarkers() {
        List<Marker> markers = markerService.getMarkers();
        return new ResponseEntity<>(markers, HttpStatus.OK);
    }

    @Operation(summary = "Returns park location by given id")
    @GetMapping("/{id}")
    public ResponseEntity<Marker> getById(@PathVariable Long id) {
        Marker marker = markerService.getMarkerById(id);
        return new ResponseEntity<>(marker, HttpStatus.OK);
    }

    @Operation(summary = "Returns park location by given latitude and longitude")
    @GetMapping(value = "/getMarker", produces = "application/json")
    public ResponseEntity<Marker> getMarker(@RequestParam("lat") double latitude,
                                            @RequestParam("lng") double longitude) {
        Marker markerByLatLng = markerService.getMarkerByLatLng(latitude, longitude);
        return new ResponseEntity<>(markerByLatLng, HttpStatus.OK);
    }

    @Operation(summary = "Returns all available park locations for given datetime range")
    @GetMapping(value = "/search", produces = "application/json")
    public ResponseEntity<List<Marker>> getAllByDateRange(
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTo) {
        List<Marker> allBySelectedDateRange = markerService.getAllBySelectedDateRange(dateFrom, dateTo);
        return new ResponseEntity<>(allBySelectedDateRange, HttpStatus.OK);
    }

    @Operation(summary = "Makes reservation on a park location")
    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> makeReservation(@RequestBody ReservationRequest reservationRequest) {
        markerService.makeReservation(reservationRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Updates park location data")
    @PutMapping(value = "/update")
    public ResponseEntity<String> updateMarker(@RequestBody UpdateRequest updateRequest) {
        markerService.updateMarker(updateRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Deletes park location by given id")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteMarker(@PathVariable Long id) {
        markerService.deleteMarkerById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
