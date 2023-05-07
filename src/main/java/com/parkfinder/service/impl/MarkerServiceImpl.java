package com.parkfinder.service.impl;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.Reservation;
import com.parkfinder.model.ReservationRequest;
import com.parkfinder.model.UpdateRequest;
import com.parkfinder.model.dto.MarkerDTO;
import com.parkfinder.repository.MarkerRepository;
import com.parkfinder.repository.ReservationRepository;
import com.parkfinder.service.ExtendedMarkerService;
import com.parkfinder.util.ReservationUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static com.parkfinder.util.DtoToEntityConverter.getMarkerEntity;
import static com.parkfinder.util.DtoToEntityConverter.getUpdatedMarker;

@Service
@Transactional // Fix for 403 error.
@RequiredArgsConstructor
public class MarkerServiceImpl implements ExtendedMarkerService {

    private final MarkerRepository markerRepository;
    private final ReservationRepository reservationRepository;

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

    public void updateMarker(UpdateRequest updateRequest) {
        Marker marker = getMarkerById(updateRequest.getId());
        Marker updatedMarker = getUpdatedMarker(marker, updateRequest);
        markerRepository.save(updatedMarker);
    }

    public void makeReservation(ReservationRequest reservationRequest) {

        if (reservationRequest == null) {
            throw new IllegalArgumentException("Reservation request cannot be null");
        }

        Marker parkingToBeReserved = getMarkerById(reservationRequest.getId());

        if (parkingToBeReserved != null) {
            List<Reservation> reservations = parkingToBeReserved.getReservations();
            Reservation reservation = ReservationUtil.getReservationFromRequest(reservationRequest);
            reservations.add(reservation);

            parkingToBeReserved.setReservations(reservations);

            reservation.setMarker(parkingToBeReserved);
            reservationRepository.save(reservation);
        }
    }

    public void deleteMarker(MarkerDTO markerDTO) {
        Marker markerToBeDeleted = getMarkerEntity(markerDTO);
        markerRepository.delete(markerToBeDeleted);
    }

    public void deleteMarkerById(Long id) {
        markerRepository.deleteById(id);
    }

    public List<Marker> getAllBySelectedDateRange(LocalDateTime dateFrom, LocalDateTime dateTo) {
        return markerRepository.findByDateBetween(dateFrom, dateTo);
    }

    public boolean isDuplicateEntry(MarkerDTO marker) {
        return markerRepository.findByLatitudeAndLongitude(marker.getLatitude(), marker.getLongitude()) != null;
    }
}
