package com.parkfinder.repository;

import com.parkfinder.entity.Marker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MarkerRepositoryTest {
    @Mock
    private MarkerRepository markerRepository;

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
    void testFindByDateBetween() {
        // Given
        LocalDateTime dateFrom = LocalDateTime.now();
        LocalDateTime dateTo = dateFrom.plusDays(1);

        Marker marker1 = new Marker();
        marker1.setDateFrom(dateFrom.minusDays(1));
        marker1.setDateTo(dateFrom.plusDays(1));

        Marker marker2 = new Marker();
        marker2.setDateFrom(dateFrom.plusDays(1));
        marker2.setDateTo(dateTo.plusDays(1));

        // When
        when(markerRepository.findByDateBetween(dateFrom, dateTo)).thenReturn(Arrays.asList(marker1, marker2));

        // Then
        List<Marker> foundMarkers = markerRepository.findByDateBetween(dateFrom, dateTo);
        assertNotNull(foundMarkers);
        assertEquals(2, foundMarkers.size());
        assertEquals(marker1.getDateFrom(), foundMarkers.get(0).getDateFrom());
        assertEquals(marker1.getDateTo(), foundMarkers.get(0).getDateTo());
        assertEquals(marker2.getDateFrom(), foundMarkers.get(1).getDateFrom());
        assertEquals(marker2.getDateTo(), foundMarkers.get(1).getDateTo());
    }
}
