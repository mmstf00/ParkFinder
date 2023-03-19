package com.parkfinder.controller.mvc;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.User;
import com.parkfinder.service.MarkerService;
import com.parkfinder.service.UserService;
import org.junit.jupiter.api.BeforeEach;
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

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ConfirmReservationControllerTest {

    @Mock
    private UserService userService;
    @Mock
    private MarkerService markerService;
    @Mock
    private Model model;
    @InjectMocks
    private ConfirmReservationController confirmReservationController;

    private Marker marker;

    @BeforeEach
    void init() {
        marker = new Marker();
        marker.setId(123L);
        marker.setAddress("123 Main St");
        marker.setPlaceId("abc123");
        marker.setPriceTag(5.0);
        marker.setDateFrom(LocalDateTime.now());
        marker.setDateTo(LocalDateTime.now().plusDays(1));
        marker.setLatitude(38.8977);
        marker.setLongitude(77.0365);
        marker.setReservable(true);
    }

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
        when(markerService.getMarkerById(123L)).thenReturn(marker);

        // Act
        String result = confirmReservationController.getConfirmationPage(model, "123");

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
        when(markerService.getMarkerById(123L)).thenReturn(marker);

        // Act
        String result = confirmReservationController.getConfirmationPage(model, "123");

        // Assert
        assertEquals("confirm-reservation", result);
        Mockito.verify(userService).getUserByEmail("test@example.com");
        Mockito.verify(model, Mockito.never()).addAttribute("user", null);
    }
}
