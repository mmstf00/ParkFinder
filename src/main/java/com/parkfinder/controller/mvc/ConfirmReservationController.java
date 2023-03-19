package com.parkfinder.controller.mvc;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.User;
import com.parkfinder.service.MarkerService;
import com.parkfinder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.Duration;
import java.util.Optional;

@Controller
@RequestMapping("/confirmation")
@RequiredArgsConstructor
public class ConfirmReservationController {

    private final UserService userService;
    private final MarkerService markerService;

    @GetMapping
    public String getConfirmationPage(Model model, @RequestParam("parkingId") String parkingId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Fetch user from the database using the email and pass it to thymeleaf template.
        Optional<User> user = userService.getUserByEmail(email);
        user.ifPresent(value -> model.addAttribute("user", value));

        Marker parking = markerService.getMarkerById(Long.valueOf(parkingId));
        model.addAttribute("parking", parking);

        Duration duration = Duration.between(parking.getDateTo().toLocalTime(), parking.getDateFrom().toLocalTime()).abs();
        if (duration.toHours() == 0) {
            model.addAttribute("duration", duration.toMinutes());
            model.addAttribute("durationText", "minutes");
        } else {
            model.addAttribute("duration", duration.toHours());
            model.addAttribute("durationText", "hours");
        }

        return "confirm-reservation";
    }
}
