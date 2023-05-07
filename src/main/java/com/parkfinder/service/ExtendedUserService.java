package com.parkfinder.service;

import com.parkfinder.model.dto.UserDTO;

public interface ExtendedUserService extends UserService {
    boolean isExistingUser(UserDTO userDTO);
}
