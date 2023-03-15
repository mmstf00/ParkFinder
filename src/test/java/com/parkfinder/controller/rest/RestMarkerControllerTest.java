package com.parkfinder.controller.rest;

import com.parkfinder.entity.Marker;
import com.parkfinder.model.MarkerDTO;
import com.parkfinder.model.ReservationModel;
import com.parkfinder.service.MarkerService;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.parkfinder.util.DtoToEntityConverter.getMarkerEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
    private MarkerService markerService;
    private Marker marker;
    private Marker marker1;

    @BeforeEach
    void init() {
        restTemplate = new RestTemplate();

        MarkerDTO markerDTO = new MarkerDTO();
        markerDTO.setAddress("San Francisco");
        markerDTO.setPlaceId("abc123");
        markerDTO.setPriceTag(5.0);
        markerDTO.setDateFrom(LocalDateTime.now());
        markerDTO.setDateTo(LocalDateTime.now().plusDays(1));
        markerDTO.setLatitude(37.7749);
        markerDTO.setLongitude(-122.4194);
        markerDTO.setReservable(true);

        marker = getMarkerEntity(markerDTO);
        marker.setId(1L);

        MarkerDTO markerDTO1 = new MarkerDTO();
        markerDTO1.setAddress("New York");
        markerDTO1.setPlaceId("abc123");
        markerDTO1.setPriceTag(5.0);
        markerDTO1.setDateFrom(LocalDateTime.now());
        markerDTO1.setDateTo(LocalDateTime.now().plusDays(1));
        markerDTO1.setLatitude(40.7128);
        markerDTO1.setLongitude(-74.0060);
        markerDTO1.setReservable(true);

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
    void testUpdateMarker() throws Exception {
        ReservationModel reservation = new ReservationModel();
        reservation.setId(1L);
        reservation.setNotReserved(false);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1")
                        .contentType("application/json")
                        .content("{ \"id\": 1, \"notReserved\": false }"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(markerService, times(1))
                .updateMarkerReservationById(reservation.getId(), reservation.isNotReserved());
    }

}