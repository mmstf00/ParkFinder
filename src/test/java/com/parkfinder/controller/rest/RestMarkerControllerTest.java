package com.parkfinder.controller.rest;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.ReservationRequest;
import com.parkfinder.model.UpdateRequest;
import com.parkfinder.model.dto.MarkerDTO;
import com.parkfinder.service.ExtendedMarkerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.parkfinder.util.DtoToEntityConverter.getMarkerEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class RestMarkerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @LocalServerPort
    private int randomServerPort;
    private RestTemplate restTemplate;
    @MockBean
    private ExtendedMarkerService markerService;
    private Marker marker;
    private Marker marker1;

    @BeforeEach
    void init() {
        restTemplate = new RestTemplate();

        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setAddress("San Francisco");
        markerDTO.setPlaceId("abc123");
        markerDTO.setPriceTag(5.0);
        markerDTO.setLatitude(37.7749);
        markerDTO.setLongitude(-122.4194);

        marker = getMarkerEntity(markerDTO);
        marker.setId(1L);

        MarkerDTO markerDTO1 = new MarkerDTO();
        markerDTO1.setAddress("New York");
        markerDTO1.setPlaceId("abc123");
        markerDTO1.setPriceTag(5.0);
        markerDTO1.setLatitude(40.7128);
        markerDTO1.setLongitude(-74.0060);

        marker1 = getMarkerEntity(markerDTO1);
        marker1.setId(2L);
    }

    @Test
    void testGetAllMarkers() {
        List<Marker> markers = new ArrayList<>();
        markers.add(marker);
        markers.add(marker1);

        when(markerService.getMarkers()).thenReturn(markers);

        String url = "http://localhost:" + randomServerPort + "/api/v1";
        ResponseEntity<List<Marker>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                });

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(markers, response.getBody());
    }

    @Test
    void testGetMarker() {
        double latitude = 37.7749;
        double longitude = -122.4194;
        Marker tempMarker = marker;
        tempMarker.setLatitude(latitude);
        tempMarker.setLongitude(longitude);


        when(markerService.getMarkerByLatLng(latitude, longitude)).thenReturn(tempMarker);

        String url = "http://localhost:" + randomServerPort + "/api/v1/getMarker?lat={lat}&lng={lng}";
        ResponseEntity<Marker> response = restTemplate.getForEntity(
                url,
                Marker.class,
                latitude,
                longitude);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tempMarker, response.getBody());
    }

    @Test
    void testGetMarkerById() {
        Long id = 2L;
        Marker tempMarker = marker;
        tempMarker.setId(2L);


        when(markerService.getMarkerById(id)).thenReturn(tempMarker);

        String url = "http://localhost:" + randomServerPort + "/api/v1/{id}";
        ResponseEntity<Marker> response = restTemplate.getForEntity(
                url,
                Marker.class,
                id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(tempMarker, response.getBody());
    }

    @Test
    void testGetAllByDateRange() {
        LocalDateTime dateFrom = LocalDateTime.of(2022, 1, 1, 0, 0, 0);
        LocalDateTime dateTo = LocalDateTime.of(2022, 1, 2, 0, 0, 0);
        List<Marker> markers = new ArrayList<>();
        markers.add(marker);

        when(markerService.getAllBySelectedDateRange(dateFrom, dateTo)).thenReturn(markers);

        String url = "http://localhost:" + randomServerPort + "/api/v1/search?dateFrom={dateFrom}&dateTo={dateTo}";
        ResponseEntity<List<Marker>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<>() {
                },
                dateFrom.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                dateTo.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(markers, response.getBody());
    }

    @Test
    void testMakeReservation() throws Exception {
        ReservationRequest reservationRequest = new ReservationRequest();
        reservationRequest.setId(1L);
        reservationRequest.setPlateNumber("H4595BH");
        reservationRequest.setDateFrom(LocalDate.of(2023, 3, 25));
        reservationRequest.setTimeFrom(LocalTime.of(11, 30));
        reservationRequest.setDateTo(LocalDate.of(2023, 3, 25));
        reservationRequest.setTimeTo(LocalTime.of(12, 30));

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1")
                .contentType("application/json")
                .content("{ \"id\": 1, \"plateNumber\": \"H4595BH\", " +
                        "\"dateFrom\": \"2023-03-25\", \"timeFrom\": \"11:30:00\", " +
                        "\"dateTo\": \"2023-03-25\", \"timeTo\": \"12:30:00\" }"
                )).andExpect(MockMvcResultMatchers.status().isOk());

        verify(markerService, times(1))
                .makeReservation(reservationRequest);
    }

    @Test
    void testUpdateReservation() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest();
        updateRequest.setId(1L);
        updateRequest.setAddress("Test Update Address");
        updateRequest.setDetails("Test Update Details");
        updateRequest.setPrice(12.34);

        doNothing().when(markerService).updateMarker(updateRequest);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/update")
                        .contentType(APPLICATION_JSON)
                        .content("{\"id\":1," +
                                "\"address\":\"Test Update Address\"," +
                                "\"details\":\"Test Update Details\"," +
                                "\"price\":12.34}")
                        .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(markerService, times(1)).updateMarker(updateRequest);
        verifyNoMoreInteractions(markerService);
    }

    @Test
    void testDeleteMarker() throws Exception {
        Long markerId = 1L;

        doNothing().when(markerService).deleteMarkerById(markerId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/v1/{id}", markerId)
                        .accept(APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(markerService, times(1)).deleteMarkerById(markerId);
        verifyNoMoreInteractions(markerService);
    }
}