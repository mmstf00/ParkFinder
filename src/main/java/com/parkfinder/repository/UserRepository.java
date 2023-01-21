package com.parkfinder.repository;

import com.parkfinder.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    boolean existsUserByUsername(String userName);
}
