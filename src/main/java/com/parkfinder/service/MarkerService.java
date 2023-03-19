package com.parkfinder.service;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.MarkerDTO;
import com.parkfinder.repository.MarkerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    public Marker getMarkerByLatLng(double latitude, double longitude) {
        return markerRepository.findByLatitudeAndLongitude(latitude, longitude);
    }

    public Marker getMarkerById(Long id) {
        return markerRepository.findById(id).orElse(null);
    }

    public void updateMarker(MarkerDTO markerDTO) {
        Marker markerToBeUpdated = getMarkerEntity(markerDTO);
        markerRepository.save(markerToBeUpdated);
    }

    public void updateMarkerReservationById(Long id, boolean reservation) {
        Marker markerToBeUpdated = getMarkerById(id);
        if (markerToBeUpdated != null) {
            markerToBeUpdated.setReservable(reservation);
            markerRepository.save(markerToBeUpdated);
        }
    }

    public void deleteMarker(MarkerDTO markerDTO) {
        Marker markerToBeDeleted = getMarkerEntity(markerDTO);
        markerRepository.delete(markerToBeDeleted);
    }

    public List<Marker> getAllBySelectedDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return markerRepository.findByDateBetween(dateFrom, dateTo);
    }

    public boolean isDuplicateEntry(MarkerDTO marker) {
        return markerRepository.findByLatitudeAndLongitude(marker.getLatitude(), marker.getLongitude()) != null;
    }
}
