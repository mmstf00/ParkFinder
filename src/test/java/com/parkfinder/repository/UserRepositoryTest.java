package com.parkfinder.repository;


import com.parkfinder.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @Test
    void testFindUserByUsername() {
        // Given
        String username = "testUser";
        User user = new User();
        user.setEmail("testEmail");
        user.setUsername(username);
        user.setPassword("testPassword");
        user.setRoles("ROLE_USER");
        when(userRepository.findUserByUsername(username)).thenReturn(Optional.of(user));

        // When
        Optional<User> foundUser = userRepository.findUserByUsername(username);

        // Then
        assertTrue(foundUser.isPresent());
        assertEquals(user.getUsername(), foundUser.get().getUsername());
        assertEquals(user.getPassword(), foundUser.get().getPassword());
        assertEquals(user.getEmail(), foundUser.get().getEmail());
        assertEquals(user.getRoles(), foundUser.get().getRoles());
    }

    @Test
    void testExistsUserByEmail() {
        // Given
        String email = "testEmail";
        when(userRepository.existsUserByEmail(email)).thenReturn(true);

        // When
        boolean existsUser = userRepository.existsUserByEmail(email);

        // Then
        assertTrue(existsUser);

        // Given
        String notExistingEmail = "notExistingEmail";
        when(userRepository.existsUserByEmail(notExistingEmail)).thenReturn(false);

        // When
        existsUser = userRepository.existsUserByEmail(notExistingEmail);

        // Then
        assertFalse(existsUser);
    }
}
