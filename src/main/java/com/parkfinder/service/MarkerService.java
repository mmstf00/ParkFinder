package com.parkfinder.service;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.MarkerDTO;
import com.parkfinder.repository.MarkerRepository;
import com.parkfinder.util.DtoToEntityConverter;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkerService {

    private final MarkerRepository markerRepository;

    public MarkerService(MarkerRepository markerRepository) {
        this.markerRepository = markerRepository;
    }

    public void addMarker(MarkerDTO markerDTO) {
        Marker marker = mapMarkerDTOToEntity(markerDTO);
        markerRepository.save(marker);
    }

    public List<Marker> getMarkers() {
        return markerRepository.findAll();
    }

    public Marker updateMarker(MarkerDTO markerDTO) {
        Marker updatedMarker = mapMarkerDTOToEntity(markerDTO);
        return markerRepository.save(updatedMarker);
    }

    public void deleteMarker(MarkerDTO markerDTO) {
        Marker markerToBeDeleted = mapMarkerDTOToEntity(markerDTO);
        markerRepository.delete(markerToBeDeleted);
    }

    public Marker mapMarkerDTOToEntity(MarkerDTO markerDTO) {
        return DtoToEntityConverter.getMarkerEntity(markerDTO);
    }

    public boolean isDuplicateEntry(MarkerDTO marker) {
        return markerRepository.findByLatitudeAndLongitude(marker.getLatitude(), marker.getLongitude()) != null;
    }
}
