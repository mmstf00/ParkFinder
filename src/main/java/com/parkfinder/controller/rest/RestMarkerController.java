package com.parkfinder.controller.rest;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.ReservationModel;
import com.parkfinder.service.MarkerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<String> updateMarker(@RequestBody ReservationModel reservation) {
        markerService.updateMarkerReservationById(reservation.getId(), reservation.isNotReserved());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
