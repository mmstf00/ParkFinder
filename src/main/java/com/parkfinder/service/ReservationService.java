package com.parkfinder.service;

import com.parkfinder.entity.Reservation;
import com.parkfinder.model.paging.Paged;

public interface ReservationService {
    Paged<Reservation> getReservations(int pageNumber, int size);
}
