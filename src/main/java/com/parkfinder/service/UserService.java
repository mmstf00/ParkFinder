package com.parkfinder.service;

import com.parkfinder.entity.User;
import com.parkfinder.model.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    void saveUser(UserDTO userDTO);

    Optional<User> getUserByEmail(String email);
}
