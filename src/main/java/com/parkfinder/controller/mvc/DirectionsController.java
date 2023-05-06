package com.parkfinder.controller.mvc;

import com.parkfinder.entity.User;
import com.parkfinder.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/directions")
@RequiredArgsConstructor
public class DirectionsController {

    private final UserService userService;

    @GetMapping
    public String getDirections(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        // Fetch user from the database using the email and pass it to thymeleaf template.
        Optional<User> user = userService.getUserByEmail(email);
        user.ifPresent(value -> model.addAttribute("user", value));
        return "directions";
    }
}
