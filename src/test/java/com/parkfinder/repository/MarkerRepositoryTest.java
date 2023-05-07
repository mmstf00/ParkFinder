package com.parkfinder.repository;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.Reservation;
import com.parkfinder.service.ExtendedMarkerService;
import com.parkfinder.service.impl.MarkerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarkerRepositoryTest {
    @Mock
    private MarkerRepository markerRepository;
    @Mock
    private ReservationRepository reservationRepository;
    private ExtendedMarkerService markerService;

    @BeforeEach
    void setup() {
        markerService = new MarkerServiceImpl(markerRepository, reservationRepository);
    }

    @Test
    void testFindByLatitudeAndLongitude() {
        // Given
        Marker marker = new Marker();
        marker.setLatitude(1.0);
        marker.setLongitude(2.0);

        // When
        when(markerRepository.findByLatitudeAndLongitude(1.0, 2.0)).thenReturn(marker);

        // Then
        Marker foundMarker = markerRepository.findByLatitudeAndLongitude(1.0, 2.0);
        assertNotNull(foundMarker);
        assertEquals(marker.getLatitude(), foundMarker.getLatitude());
        assertEquals(marker.getLongitude(), foundMarker.getLongitude());
    }

    @Test
    void testFindAvailableMarkersInRange() {
        // Create some sample data
        LocalDateTime dateFrom = LocalDateTime.of(2023, 3, 25, 12, 0);
        LocalDateTime dateTo = LocalDateTime.of(2023, 3, 27, 12, 0);

        Marker marker1 = new Marker();
        marker1.setId(1L);
        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setDateFrom(LocalDateTime.of(2023, 3, 24, 12, 0));
        reservation1.setDateTo(LocalDateTime.of(2023, 3, 26, 12, 0));
        List<Reservation> reservations1 = new ArrayList<>();
        reservations1.add(reservation1);
        marker1.setReservations(reservations1);

        Marker marker2 = new Marker();
        marker2.setId(2L);
        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setDateFrom(LocalDateTime.of(2023, 3, 26, 12, 0));
        reservation2.setDateTo(LocalDateTime.of(2023, 3, 28, 12, 0));
        List<Reservation> reservations2 = new ArrayList<>();
        reservations2.add(reservation2);
        marker2.setReservations(reservations2);

        Marker marker3 = new Marker();
        marker3.setId(3L);
        Reservation reservation3 = new Reservation();
        reservation3.setId(3L);
        reservation3.setDateFrom(LocalDateTime.of(2023, 3, 28, 12, 0));
        reservation3.setDateTo(LocalDateTime.of(2023, 3, 30, 12, 0));
        List<Reservation> reservations3 = new ArrayList<>();
        reservations3.add(reservation3);
        marker3.setReservations(reservations3);

        List<Marker> expectedMarkers = new ArrayList<>();
        expectedMarkers.add(marker1);
        expectedMarkers.add(marker2);
        expectedMarkers.add(marker3);

        // Configure the mock repository to return the sample data
        when(markerRepository.findByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(expectedMarkers);

        // Call the service method
        List<Marker> actualMarkers = markerService.getAllBySelectedDateRange(dateFrom, dateTo);

        // Verify the result
        assertEquals(expectedMarkers, actualMarkers);
    }


}
