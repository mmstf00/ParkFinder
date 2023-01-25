package com.parkfinder.service;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.MarkerDTO;
import com.parkfinder.repository.MarkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.parkfinder.util.DtoToEntityConverter.getMarkerEntity;

@Service
@Transactional // Fix for 403 error.
public class MarkerService {

    private final MarkerRepository markerRepository;

    public MarkerService(MarkerRepository markerRepository) {
        this.markerRepository = markerRepository;
    }

    public void addMarker(MarkerDTO markerDTO) {
        Marker marker = getMarkerEntity(markerDTO);
        markerRepository.save(marker);
    }

    public List<Marker> getMarkers() {
        return markerRepository.findAll();
    }

    public void updateMarker(MarkerDTO markerDTO) {
        Marker updatedMarker = getMarkerEntity(markerDTO);
        markerRepository.save(updatedMarker);
    }

    public void updateMarkerReservationById(Long id, boolean reservation) {
        Marker updatedMarker = markerRepository.getReferenceById(id);
        updatedMarker.setReservable(reservation);
        markerRepository.save(updatedMarker);
    }

    public void deleteMarker(MarkerDTO markerDTO) {
        Marker markerToBeDeleted = getMarkerEntity(markerDTO);
        markerRepository.delete(markerToBeDeleted);
    }

    public boolean isDuplicateEntry(MarkerDTO marker) {
        return markerRepository.findByLatitudeAndLongitude(marker.getLatitude(), marker.getLongitude()) != null;
    }
}
