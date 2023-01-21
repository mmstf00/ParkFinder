package com.parkfinder.service;

import com.parkfinder.entity.User;
import com.parkfinder.exception.UserExistsException;
import com.parkfinder.model.UserDTO;
import com.parkfinder.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.parkfinder.util.DtoToEntityConverter.getUserEntity;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUser(UserDTO userDTO) throws UserExistsException {
        User user = getUserEntity(userDTO);
        if (userRepository.existsUserByUsername(user.getUsername())) {
            throw new UserExistsException("User with this username exists!");
        }
        userRepository.save(user);
    }
}
