package com.parkfinder.jobs;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.Reservation;
import com.parkfinder.repository.MarkerRepository;
import com.parkfinder.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpiredReservationsCleanupJob implements Job {

    private final MarkerRepository markerRepository;
    private final ReservationRepository reservationRepository;


    /*
     * Adjusts the available slots, by doing look-up for expired reservations.
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) {
        LocalDateTime currentTime = LocalDateTime.now();

        // Retrieve reservations which has dateTo bigger than current date
        List<Reservation> expiredReservations = reservationRepository.findAllByDateToBefore(currentTime);

        for (Reservation reservation : expiredReservations) {
            Marker parking = reservation.getMarker();

            // Update only when max parking size is not reached and reservation is not expired
            if (isAvailableForCleanUp(parking, reservation)) {
                // Increment the available lot count
                int availableLots = parking.getUsedLots() - 1;
                parking.setUsedLots(availableLots);

                reservation.setExpired(true);
                reservationRepository.save(reservation);

                markerRepository.save(parking);
            }
        }
    }

    /*
     * Checks if the parking is available and reservation is not expired.
     * The flag isExpired is used to do look-up for expired reservations
     * and do not count them when adjusting the available slots
     */
    private boolean isAvailableForCleanUp(Marker parking, Reservation reservation) {
        return (parking.getUsedLots() <= parking.getParkSize())
                && parking.getUsedLots() > 0 // To prevent usedLots to be negative value
                && !reservation.isExpired();
    }
}
