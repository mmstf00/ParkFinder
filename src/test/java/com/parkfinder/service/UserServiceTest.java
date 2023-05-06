package com.parkfinder.service;

import com.parkfinder.entity.User;
import com.parkfinder.model.dto.UserDTO;
import com.parkfinder.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserService userService;
    private UserDTO userDTO;
    private User user;

    @BeforeEach
    void init() {
        userDTO = new UserDTO();
        userDTO.setId(123L);
        userDTO.setEmail("john.doe@example.com");
        userDTO.setUsername("John Doe");
        userDTO.setPassword("12345678");
        userDTO.setRoles("ROLE_USER");

        user = new User();
        user.setId(123L);
        user.setEmail("john.doe@example.com");
        user.setUsername("John Doe");
        user.setPassword("12345678");
        user.setRoles("ROLE_USER");
    }

    @Test
    void testSaveUser() {
        // Given
        User expectedUser = userRepository.save(user);

        // When
        userService.saveUser(userDTO);

        // Then
        assertEquals(expectedUser, userRepository.save(user));
    }

    @Test
    void testIsExistingUserReturnsTrue() {
        // Given
        when(userRepository.existsUserByEmail(user.getEmail())).thenReturn(true);

        // When
        boolean result = userService.isExistingUser(userDTO);

        // Then
        verify(userRepository).existsUserByEmail(user.getEmail());
        assertTrue(result);
    }

    @Test
    void testIsExistingUserReturnsFalse() {
        // Given
        when(userRepository.existsUserByEmail(user.getEmail())).thenReturn(false);

        // When
        boolean result = userService.isExistingUser(userDTO);

        // Then
        verify(userRepository).existsUserByEmail(user.getEmail());
        assertFalse(result);
    }

    @Test
    void testGetUserByEmail() {
        // Given
        User testUser = new User();
        testUser.setEmail("test@example.com");

        // When
        when(userRepository.findUserByEmail("test@example.com")).thenReturn(Optional.of(testUser));

        // Then
        Optional<User> result = userService.getUserByEmail("test@example.com");
        verify(userRepository).findUserByEmail("test@example.com");
        assertTrue(result.isPresent());
        assertEquals(testUser, result.get());
    }
}