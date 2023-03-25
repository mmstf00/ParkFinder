package com.parkfinder.repository;

import com.parkfinder.entity.Marker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MarkerRepository extends JpaRepository<Marker, Long> {
    Marker findByLatitudeAndLongitude(double latitude, double longitude);


    /**
     * Returns a list of available markers that do not have any reservations
     * overlapping with the specified date range.
     *
     * @param dateFrom the start of the date range to check for availability.
     * @param dateTo   the end of the date range to check for availability.
     * @return a list of available markers.
     */
    @Query("SELECT DISTINCT m FROM Marker m " +
            "LEFT JOIN m.reservations r " +
            "ON ((r.dateFrom BETWEEN :dateFrom AND :dateTo) OR " +
            "(r.dateTo BETWEEN :dateFrom AND :dateTo) OR " +
            "(:dateFrom BETWEEN r.dateFrom AND r.dateTo) OR " +
            "(:dateTo BETWEEN r.dateFrom AND r.dateTo)) " +
            "WHERE r.id IS NULL")
    List<Marker> findByDateBetween(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);
}
