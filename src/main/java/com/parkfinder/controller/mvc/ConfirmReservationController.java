package com.parkfinder.controller.mvc;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.User;
import com.parkfinder.model.ConfirmReservationRequest;
import com.parkfinder.service.MarkerService;
import com.parkfinder.service.UserService;
import com.parkfinder.util.DateTimeUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Duration;
import java.util.Optional;

@Controller
@RequestMapping("/confirmation")
@RequiredArgsConstructor
public class ConfirmReservationController {

    private final UserService userService;
    private final MarkerService markerService;
    private Marker currentParking;

    @GetMapping
    public String getConfirmationPage(Model model, @ModelAttribute ConfirmReservationRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Fetch user from the database using the email and pass it to thymeleaf template.
        Optional<User> user = userService.getUserByEmail(email);
        user.ifPresent(value -> model.addAttribute("user", value));

        currentParking = markerService.getMarkerById(request.getParkingId());
        model.addAttribute("parking", currentParking);

        Duration duration = DateTimeUtil.getParkingDurationFromRequest(request);
        if (duration.toHours() == 0) {
            model.addAttribute("duration", duration.toMinutes());
            model.addAttribute("durationText", "minutes");
        } else {
            model.addAttribute("duration", duration.toHours());
            model.addAttribute("durationText", "hours");
        }

        return "confirm-reservation";
    }

    @GetMapping("/success")
    public String successfulReservationPage(Model model) {
        model.addAttribute("parking", currentParking);
        return "successful-reservation";
    }
}
