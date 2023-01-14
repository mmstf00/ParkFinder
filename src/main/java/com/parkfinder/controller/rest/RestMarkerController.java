package com.parkfinder.controller.rest;

import com.parkfinder.entity.Marker;
import com.parkfinder.service.MarkerService;
import org.springframework.web.bind.annotation.GetMapping;
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
}
