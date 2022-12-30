package com.parkfinder.service;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.MarkerDTO;
import com.parkfinder.repository.MarkerRepository;
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
        Marker marker = new Marker();
        marker.setPriceTag("$1.40");
        Marker marker1 = new Marker();
        marker1.setPriceTag("$1.50");
        Marker marker2 = new Marker();
        marker2.setPriceTag("$1.70");
        Marker marker3 = new Marker();
        marker3.setPriceTag("$1.10");

        // return markerRepository.findAll();
        return List.of(marker, marker1, marker2, marker3);
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
        Marker marker = new Marker();
        marker.setPriceTag(markerDTO.getPriceTag());
        marker.setLatitude(markerDTO.getLatitude());
        marker.setLongitude(markerDTO.getLongitude());
        return marker;
    }
}
