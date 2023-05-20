package com.parkfinder.service;

import com.parkfinder.entity.Reservation;
import com.parkfinder.model.paging.Paged;
import com.parkfinder.repository.ReservationRepository;
import com.parkfinder.service.impl.ReservationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ReservationServiceImplTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationServiceImpl reservationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetReservations() {
        // Mock data
        List<Reservation> reservations = new ArrayList<>();

        Reservation reservation1 = new Reservation();
        reservation1.setId(1L);
        reservation1.setDateFrom(LocalDateTime.of(2023, 3, 24, 12, 0));
        reservation1.setDateTo(LocalDateTime.of(2023, 3, 26, 12, 0));

        Reservation reservation2 = new Reservation();
        reservation2.setId(2L);
        reservation2.setDateFrom(LocalDateTime.of(2023, 3, 26, 12, 0));
        reservation2.setDateTo(LocalDateTime.of(2023, 3, 28, 12, 0));

        reservations.add(reservation1);
        reservations.add(reservation2);

        // Set up mock behavior
        when(reservationRepository.findAll()).thenReturn(reservations);

        // Call the method under test
        Paged<Reservation> result = reservationService.getReservations(1, 2);

        // Verify the interactions and assertions
        verify(reservationRepository, times(1)).findAll();

        assertEquals(2, result.getPage().getContent().size());
        assertEquals(1, result.getPaging().getPageNumber());
        assertEquals(2, result.getPaging().getPageSize());
    }
}