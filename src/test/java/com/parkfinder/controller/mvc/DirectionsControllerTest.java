package com.parkfinder.controller.mvc;

import com.parkfinder.entity.User;
import com.parkfinder.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DirectionsControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private DirectionsController directionsController;

    @Test
    void testGetDirections() {
        // Create a mock Authentication object and set it on the SecurityContextHolder
        Authentication authentication = Mockito.mock(Authentication.class);
        Mockito.when(authentication.getName()).thenReturn("user@example.com");
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Create a mock User object to be returned by the UserService
        User user = new User();
        user.setEmail("user@example.com");
        user.setUsername("Test User");
        Mockito.when(userService.getUserByEmail("user@example.com")).thenReturn(Optional.of(user));

        // Create a mock Model object
        Model model = Mockito.mock(Model.class);

        // Call the getDirections() method on the DirectionsController
        String result = directionsController.getDirections(model);

        // Verify that the correct view name was returned
        assertEquals("directions", result);

        // Verify that the UserService was called with the correct email address
        Mockito.verify(userService).getUserByEmail("user@example.com");

        // Verify that the User object was added to the Model with the correct name
        Mockito.verify(model).addAttribute("user", user);
    }
}
