package com.parkfinder.controller.mvc;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.MarkerDTO;
import com.parkfinder.service.MarkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class MarkerConfigurationControllerTest {

    @Mock
    private MarkerService markerService;
    @Mock
    private Model model;
    @InjectMocks
    private MarkerConfigurationController markerConfigurationController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddMarker_shouldReturnMarkerConfigurationPageWithAddedMarker() {
        // Arrange
        MarkerDTO markerDTO = new MarkerDTO();
        BindingResult result = Mockito.mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(markerService.isDuplicateEntry(markerDTO)).thenReturn(false);

        // Act
        String resultPage = markerConfigurationController.addMarker(model, markerDTO, result);

        // Assert
        verify(markerService).addMarker(markerDTO);
        assertEquals("markerConfigurationPage", resultPage);
    }

    @Test
    void testAddMarker_shouldReturnMarkerConfigurationPageWithError() {
        // Arrange
        MarkerDTO markerDTO = new MarkerDTO();
        BindingResult result = Mockito.mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(true);

        // Act
        String resultPage = markerConfigurationController.addMarker(model, markerDTO, result);

        // Assert
        assertEquals("markerConfigurationPage", resultPage);
    }

    @Test
    void testAddDuplicateMarker_shouldReturnMarkerConfigurationPageWithError() {
        // Arrange
        MarkerDTO markerDTO = new MarkerDTO();
        BindingResult result = Mockito.mock(BindingResult.class);
        when(result.hasErrors()).thenReturn(false);
        when(markerService.isDuplicateEntry(markerDTO)).thenReturn(true);

        // Act
        String resultPage = markerConfigurationController.addMarker(model, markerDTO, result);

        // Assert
        verify(model).addAttribute("duplicateError", "Park location already exists");
        assertEquals("markerConfigurationPage", resultPage);
    }

    @Test
    void testGetMarkers_shouldReturnMarkerConfigurationPageWithAllMarkers() {
        // Arrange
        List<Marker> allMarkersList = new ArrayList<>();
        when(markerService.getMarkers()).thenReturn(allMarkersList);

        // Act
        String resultPage = markerConfigurationController.getMarkers(model, new MarkerDTO());

        // Assert
        verify(model).addAttribute("allMarkers", allMarkersList);
        assertEquals("markerConfigurationPage", resultPage);
    }

    @Test
    void testUpdateMarker() {
        // Arrange
        MarkerDTO markerDTO = new MarkerDTO();

        // Act
        String resultPage = markerConfigurationController.updateMarker(markerDTO);

        // Assert
        verify(markerService).updateMarker(markerDTO);
        assertEquals("markerConfigurationPage", resultPage);
    }

    @Test
    void testDeleteMarker() {
        // Arrange
        MarkerDTO markerDTO = new MarkerDTO();

        // Act
        String resultPage = markerConfigurationController.deleteMarker(markerDTO);

        // Assert
        verify(markerService).deleteMarker(markerDTO);
        assertEquals("markerConfigurationPage", resultPage);
    }
}
