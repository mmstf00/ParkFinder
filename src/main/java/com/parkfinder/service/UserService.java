package com.parkfinder.service;

import com.parkfinder.entity.User;
import com.parkfinder.model.UserDTO;
import com.parkfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.parkfinder.util.DtoToEntityConverter.getUserEntity;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUser(UserDTO userDTO) {
        User user = getUserEntity(userDTO);
        userRepository.save(user);
    }

    public boolean isExistingUser(UserDTO userDTO) {
        User user = getUserEntity(userDTO);
        return userRepository.existsUserByEmail(user.getEmail());
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }
}
