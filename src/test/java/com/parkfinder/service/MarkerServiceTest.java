package com.parkfinder.service;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.Reservation;
import com.parkfinder.model.ReservationRequest;
import com.parkfinder.model.UpdateRequest;
import com.parkfinder.model.dto.MarkerDTO;
import com.parkfinder.repository.MarkerRepository;
import com.parkfinder.repository.ReservationRepository;
import com.parkfinder.service.impl.MarkerServiceImpl;
import com.parkfinder.util.ReservationUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
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
    private UpdateRequest updateRequest;
    private ExtendedMarkerService markerService;
    private MarkerDTO markerDTO;
    private Marker marker;
    private List<Marker> markerList;

    @BeforeEach
    void setUp() {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setId(1L);
        reservationRequest.setPlateNumber("H4595BH");
        reservationRequest.setDateFrom(LocalDate.of(2023, 3, 25));
        reservationRequest.setTimeFrom(LocalTime.of(11, 30));
        reservationRequest.setDateTo(LocalDate.of(2023, 3, 25));
        reservationRequest.setTimeTo(LocalTime.of(12, 30));

        updateRequest = new UpdateRequest();
        updateRequest.setId(1L);
        updateRequest.setAddress("Test Update Address");
        updateRequest.setDetails("Test Update Details");
        updateRequest.setPrice(BigDecimal.valueOf(12.34));

        markerService = new MarkerServiceImpl(markerRepository, reservationRepository);

        Reservation reservation = ReservationUtil.getReservationFromRequest(reservationRequest);

        markerDTO = new MarkerDTO();
        markerDTO.setAddress("123 Main St");
        markerDTO.setPlaceId("abc123");
        markerDTO.setPriceTag(BigDecimal.valueOf(5.0));
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
    void testGetMarkerById() {
        Long id = 1L;

        Marker marker = new Marker();
        marker.setId(id);

        when(markerRepository.findById(id)).thenReturn(Optional.of(marker));

        Marker result = markerService.getMarkerById(id);
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
    void testUpdateMarkerRequest() {
        when(markerRepository.save(any(Marker.class))).thenReturn(marker);
        when(markerRepository.findById(1L)).thenReturn(Optional.ofNullable(marker));

        markerService.updateMarker(updateRequest);

        verify(markerRepository, times(1)).save(any(Marker.class));
    }

    @Test
    void testMakeReservation() {

        // Create a marker to reserve
        Marker markerToReserve = new Marker();
        markerToReserve.setId(1L);
        markerToReserve.setReservations(new ArrayList<>());

        // Set up the mock marker repository to return the marker to reserve
        when(markerRepository.findById(1L)).thenReturn(Optional.of(markerToReserve));

        // Create a reservation request
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setId(1L);
        reservationRequest.setDateFrom(LocalDate.now());
        reservationRequest.setTimeFrom(LocalTime.now());
        reservationRequest.setDateTo(LocalDate.now().plusDays(1));
        reservationRequest.setTimeTo(LocalTime.now().plusHours(1));
        reservationRequest.setPlateNumber("H1234BH");

        // Call the makeReservation method
        markerService.makeReservation(reservationRequest);


        // Verify that the marker repository's findById method was called with the correct argument
        verify(markerRepository).findById(1L);

        // Verify that the reservation repository's save method was called with the correct argument
        verify(reservationRepository).save(Mockito.any(Reservation.class));

        // Verify that the marker's reservations list was updated correctly
        assertEquals(1, markerToReserve.getReservations().size());
        assertEquals("H1234BH", markerToReserve.getReservations().get(0).getPlateNumber());
    }

    @Test
    void testMakeReservationWithNullRequest() {
        assertThrows(IllegalArgumentException.class, () -> {
            markerService.makeReservation(null);
        }, "Reservation request cannot be null");
    }

    @Test
    void testDeleteMarker() {
        doNothing().when(markerRepository).delete(any(Marker.class));

        markerService.deleteMarker(markerDTO);

        verify(markerRepository, times(1)).delete(any(Marker.class));
    }

    @Test
    void testDeleteMarkerById() {
        markerService.deleteMarkerById(1L);

        verify(markerRepository).deleteById(1L);
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
