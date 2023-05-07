package com.parkfinder.service;

import com.parkfinder.entity.User;
import com.parkfinder.repository.UserRepository;
import com.parkfinder.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserDetailsServiceImpl customUserDetailsService;

    @Test
    void testLoadUserByUsernameWhenUserExists() {
        // Given
        User user = new User();
        user.setUsername("testEmail");
        user.setPassword("testpassword");
        user.setRoles("ROLE_USER");
        when(userRepository.findUserByEmail("testEmail")).thenReturn(Optional.of(user));

        // When
        UserDetails userDetails = customUserDetailsService.loadUserByUsername("testEmail");

        // Then
        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    void testLoadUserByUsernameWhenUserDoesNotExist() {
        // Given
        when(userRepository.findUserByEmail("testEmail")).thenReturn(Optional.empty());

        // When and then
        assertThrows(
                UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername("testEmail"),
                "email not found testEmail"
        );
    }
}
