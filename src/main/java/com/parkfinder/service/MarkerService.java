package com.parkfinder.service;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.UpdateRequest;
import com.parkfinder.model.dto.MarkerDTO;

import java.util.List;

public interface MarkerService {
    void addMarker(MarkerDTO markerDTO);

    List<Marker> getMarkers();

    void updateMarker(MarkerDTO markerDTO);

    void updateMarker(UpdateRequest updateRequest);

    void deleteMarker(MarkerDTO markerDTO);

    void deleteMarkerById(Long id);
}
