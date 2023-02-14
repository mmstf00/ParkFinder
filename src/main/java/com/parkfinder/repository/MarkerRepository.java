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

    @Query("SELECT m FROM Marker m WHERE m.dateFrom <= ?1 AND m.dateTo >= ?2")
    List<Marker> findByDateBetween(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo);
}
