package com.parkfinder.service;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.Reservation;
import com.parkfinder.model.MarkerDTO;
import com.parkfinder.model.ReservationRequest;
import com.parkfinder.repository.MarkerRepository;
import com.parkfinder.repository.ReservationRepository;
import com.parkfinder.util.ReservationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.parkfinder.util.DtoToEntityConverter.getMarkerEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarkerServiceTest {

    @Mock
    private MarkerRepository markerRepository;
    @Mock
    private ReservationRepository reservationRepository;
    private ReservationRequest reservationRequest;
    @InjectMocks
    private MarkerService markerService;
    private MarkerDTO markerDTO;
    private Marker marker;
    private List<Marker> markerList;

    @BeforeEach
    void setUp() {
        reservationRequest = new ReservationRequest();
        reservationRequest.setId(1L);
        reservationRequest.setPlateNumber("H4595BH");
        reservationRequest.setDateFrom(LocalDate.of(2023, 3, 25));
        reservationRequest.setTimeFrom(LocalTime.of(11, 30));
        reservationRequest.setDateTo(LocalDate.of(2023, 3, 25));
        reservationRequest.setTimeTo(LocalTime.of(12, 30));

        Reservation reservation = ReservationUtil.getReservationFromRequest(reservationRequest);

        markerDTO = new MarkerDTO();
        markerDTO.setAddress("123 Main St");
        markerDTO.setPlaceId("abc123");
        markerDTO.setPriceTag(5.0);
        markerDTO.setLatitude(38.8977);
        markerDTO.setLongitude(77.0365);

        marker = getMarkerEntity(markerDTO);
        marker.setId(1L);
        marker.setReservations(new ArrayList<>(List.of(reservation)));

        markerList = new ArrayList<>();
        markerList.add(marker);
    }

    @Test
    void testAddMarker() {
        when(markerRepository.save(any(Marker.class))).thenReturn(marker);

        markerService.addMarker(markerDTO);

        verify(markerRepository, times(1)).save(any(Marker.class));
    }

    @Test
    void testGetMarkerByLatLng() {
        double latitude = 37.7749;
        double longitude = -122.4194;

        Marker marker = new Marker();
        marker.setLatitude(latitude);
        marker.setLongitude(longitude);

        when(markerRepository.findByLatitudeAndLongitude(latitude, longitude)).thenReturn(marker);

        Marker result = markerService.getMarkerByLatLng(latitude, longitude);
        assertEquals(marker, result);
    }

    @Test
    void testGetMarkers() {
        when(markerRepository.findAll()).thenReturn(markerList);

        List<Marker> result = markerService.getMarkers();

        assertEquals(result, markerList);
    }

    @Test
    void testUpdateMarker() {
        when(markerRepository.save(any(Marker.class))).thenReturn(marker);

        markerService.updateMarker(markerDTO);

        verify(markerRepository, times(1)).save(any(Marker.class));
    }

    @Test
    @Disabled("Should be moved to ReservationTest.java")
    void testUpdateMarkerReservationById() {
        when(markerRepository.findById(any(Long.class))).thenReturn(Optional.ofNullable(marker));
        when(markerRepository.save(any(Marker.class))).thenReturn(marker);

        markerService.makeReservation(reservationRequest);

        verify(markerRepository, times(1)).findById(any(Long.class));
        verify(markerRepository, times(1)).save(any(Marker.class));
    }

    @Test
    void testDeleteMarker() {
        doNothing().when(markerRepository).delete(any(Marker.class));

        markerService.deleteMarker(markerDTO);

        verify(markerRepository, times(1)).delete(any(Marker.class));
    }

    @Test
    void testGetAllBySelectedDateRange() {
        when(markerRepository.findByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class))).thenReturn(markerList);

        List<Marker> result = markerService.getAllBySelectedDateRange(LocalDateTime.now(), LocalDateTime.now().plusDays(1));

        assertEquals(result, markerList);
    }

    @Test
    void testIsDuplicateEntry() {
        when(markerRepository.findByLatitudeAndLongitude(any(Double.class), any(Double.class))).thenReturn(marker);

        boolean result = markerService.isDuplicateEntry(markerDTO);

        assertTrue(result);
    }

    @Test
    void testIsNotDuplicateEntry() {
        when(markerRepository.findByLatitudeAndLongitude(any(Double.class), any(Double.class))).thenReturn(null);

        boolean result = markerService.isDuplicateEntry(markerDTO);

        assertFalse(result);
    }
}
