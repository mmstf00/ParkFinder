package com.parkfinder.controller;

import com.parkfinder.model.MarkerDTO;
import com.parkfinder.service.MarkerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/markers")
public class MarkerController {

    private final MarkerService markerService;
    private static final String MARKER_CONFIGURATION_PAGE = "markerConfigurationPage";

    public MarkerController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @PostMapping("/addMarker")
    public String addMarker(MarkerDTO markerDTO) {
        markerService.addMarker(markerDTO);
        return MARKER_CONFIGURATION_PAGE;
    }

    @GetMapping("/getAll")
    public String getMarkers() {
        markerService.getMarkers();
        return MARKER_CONFIGURATION_PAGE;
    }

    @PutMapping("/updateMarker")
    public String updateMarker(MarkerDTO markerDTO) {
        markerService.updateMarker(markerDTO);
        return MARKER_CONFIGURATION_PAGE;
    }

    @DeleteMapping("/deleteMarker")
    public String deleteMarker(MarkerDTO markerDTO) {
        markerService.deleteMarker(markerDTO);
        return MARKER_CONFIGURATION_PAGE;
    }
}
