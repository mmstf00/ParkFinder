package com.parkfinder.controller.mvc;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.dto.MarkerDTO;
import com.parkfinder.service.ExtendedMarkerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/configureMarkers")
@RequiredArgsConstructor
public class MarkerConfigurationController {

    private final ExtendedMarkerService markerService;
    private static final String MARKER_CONFIGURATION_PAGE = "markerConfigurationPage";

    @PostMapping
    public String addMarker(Model model, @Valid @ModelAttribute("marker") MarkerDTO markerDTO, BindingResult result) {
        if (result.hasErrors()) {
            return MARKER_CONFIGURATION_PAGE;
        }
        if (markerService.isDuplicateEntry(markerDTO)) {
            model.addAttribute("duplicateError", "Park location already exists");
            return MARKER_CONFIGURATION_PAGE;
        }
        markerService.addMarker(markerDTO);
        return MARKER_CONFIGURATION_PAGE;
    }

    @GetMapping
    public String getMarkers(Model model, @ModelAttribute("marker") MarkerDTO markerDTO) {
        List<Marker> allMarkersList = markerService.getMarkers();
        model.addAttribute("allMarkers", allMarkersList);
        return MARKER_CONFIGURATION_PAGE;
    }

    @PutMapping
    public String updateMarker(@ModelAttribute("marker") MarkerDTO markerDTO) {
        markerService.updateMarker(markerDTO);
        return MARKER_CONFIGURATION_PAGE;
    }

    @DeleteMapping
    public String deleteMarker(@ModelAttribute("marker") MarkerDTO markerDTO) {
        markerService.deleteMarker(markerDTO);
        return MARKER_CONFIGURATION_PAGE;
    }
}
