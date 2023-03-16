package com.parkfinder.controller.mvc;

import com.parkfinder.entity.User;
import com.parkfinder.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfirmReservationControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private Model model;
    @InjectMocks
    private ConfirmReservationController confirmReservationController;

    @Test
    void testConfirmReservationWhenUserExists() {
        // Arrange
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@example.com", "password");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        User user = new User();
        user.setEmail("test@example.com");
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setRoles("ROLE_USER");

        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.of(user));

        // Act
        String result = confirmReservationController.getConfirmationPage(model);

        // Assert
        assertEquals("confirm-reservation", result);
        Mockito.verify(userService).getUserByEmail("test@example.com");
        Mockito.verify(model).addAttribute("user", user);
    }

    @Test
    void testConfirmReservationWhenUserNotExists() {
        // Arrange
        Authentication authentication = new UsernamePasswordAuthenticationToken("test@example.com", "password");
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);

        when(userService.getUserByEmail("test@example.com")).thenReturn(Optional.empty());

        // Act
        String result = confirmReservationController.getConfirmationPage(model);

        // Assert
        assertEquals("confirm-reservation", result);
        Mockito.verify(userService).getUserByEmail("test@example.com");
        Mockito.verify(model, Mockito.never()).addAttribute("user", null);
    }
}
