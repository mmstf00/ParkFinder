package com.parkfinder.service.impl;

import com.parkfinder.entity.Reservation;
import com.parkfinder.model.paging.Page;
import com.parkfinder.model.paging.Paged;
import com.parkfinder.model.paging.Paging;
import com.parkfinder.repository.ReservationRepository;
import com.parkfinder.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {

    private final ReservationRepository reservationRepository;

    /**
     * Retrieves reservations based on the provided page number and size.
     *
     * @param pageNumber The page number of the reservations to retrieve.
     * @param size       The number of reservations to retrieve per page.
     * @return A Paged object containing the list of reservations and pagination information.
     */
    @Override
    public Paged<Reservation> getReservations(int pageNumber, int size) {
        List<Reservation> reservations = reservationRepository.findAll();

        int totalPages = reservations.size() / size;
        int skip = pageNumber > 1 ? (pageNumber - 1) * size : 0;

        List<Reservation> pagedReservations = reservations.stream()
                .skip(skip)
                .limit(size)
                .toList();

        return new Paged<>(new Page<>(pagedReservations, totalPages), Paging.of(totalPages, pageNumber, size));
    }
}
