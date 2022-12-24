package com.parkfinder.repository;

import com.parkfinder.entity.EmptyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmptyRepository extends JpaRepository<EmptyEntity, Long> {
}
