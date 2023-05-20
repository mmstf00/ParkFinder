package com.parkfinder.service.impl;

import com.parkfinder.entity.Reservation;
import com.parkfinder.repository.ReservationRepository;
import com.parkfinder.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }
}
