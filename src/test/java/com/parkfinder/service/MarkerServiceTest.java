package com.parkfinder.service;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.MarkerDTO;
import com.parkfinder.repository.MarkerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.parkfinder.util.DtoToEntityConverter.getMarkerEntity;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MarkerServiceTest {

    @Mock
    private MarkerRepository markerRepository;
    @InjectMocks
    private MarkerService markerService;
    private MarkerDTO markerDTO;
    private Marker marker;
    private List<Marker> markerList;

    @BeforeEach
    void setUp() {

        markerDTO = new MarkerDTO();
        markerDTO.setAddress("123 Main St");
        markerDTO.setPlaceId("abc123");
        markerDTO.setPriceTag(5.0);
        markerDTO.setDateFrom(LocalDateTime.now());
        markerDTO.setDateTo(LocalDateTime.now().plusDays(1));
        markerDTO.setLatitude(38.8977);
        markerDTO.setLongitude(77.0365);
        markerDTO.setReservable(true);

        marker = getMarkerEntity(markerDTO);
        marker.setId(1L);

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
    void testUpdateMarkerReservationById() {
        when(markerRepository.getReferenceById(any(Long.class))).thenReturn(marker);
        when(markerRepository.save(any(Marker.class))).thenReturn(marker);

        markerService.updateMarkerReservationById(marker.getId(), false);

        verify(markerRepository, times(1)).getReferenceById(any(Long.class));
        verify(markerRepository, times(1)).save(any(Marker.class));
        assertFalse(marker.isReservable());
    }

    @Test
    void testDeleteMarker() {
        doNothing().when(markerRepository).delete(any(Marker.class));

        markerService.deleteMarker(markerDTO);

        verify(markerRepository, times(1)).delete(any(Marker.class));
    }

    @Test
    void testGetAllBySelectedDateRange() {
        when(markerRepository.findByDateBetween(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(markerList);

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
