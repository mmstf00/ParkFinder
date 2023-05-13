package com.parkfinder.controller.mvc;

import com.parkfinder.entity.Marker;
import com.parkfinder.entity.User;
import com.parkfinder.model.ConfirmReservationRequest;
import com.parkfinder.service.ExtendedMarkerService;
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

import java.math.BigDecimal;
import java.time.Duration;
import java.util.Optional;

import static com.parkfinder.util.ReservationUtil.calculateFinalReservationPrice;

@Controller
@RequestMapping("/confirmation")
@RequiredArgsConstructor
public class ConfirmReservationController {

    private final UserService userService;
    private final ExtendedMarkerService markerService;
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
        model.addAttribute("duration", DateTimeUtil.formatDuration(duration));

        BigDecimal finalPrice = calculateFinalReservationPrice(currentParking.getPriceTag(), duration);
        model.addAttribute("finalPrice", finalPrice);

        return "confirm-reservation";
    }

    @GetMapping("/success")
    public String successfulReservationPage(Model model) {
        model.addAttribute("parking", currentParking);
        return "successful-reservation";
    }
}
