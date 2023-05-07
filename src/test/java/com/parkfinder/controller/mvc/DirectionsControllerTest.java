package com.parkfinder.controller.mvc;

import com.parkfinder.entity.User;
import com.parkfinder.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DirectionsControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private Model model;

    @InjectMocks
    private DirectionsController controller;

    @Test
    void testGetDirections() {
        // Arrange
        String email = "test@example.com";
        User user = new User();
        user.setUsername("Test User");
        Optional<User> optionalUser = Optional.of(user);

        when(userService.getUserByEmail(email)).thenReturn(optionalUser);

        Authentication auth = new TestingAuthenticationToken(email, "password");
        SecurityContextHolder.getContext().setAuthentication(auth);

        // Act
        String result = controller.getDirections(model);

        // Assert
        verify(userService).getUserByEmail(email);
        verify(model).addAttribute("user", user);
        assertEquals("directions", result);
    }
}
