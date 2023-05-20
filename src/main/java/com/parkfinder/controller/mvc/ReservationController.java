package com.parkfinder.controller.mvc;

import com.parkfinder.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/all")
    public String getAllReservations(Model model) {
        model.addAttribute("reservations", reservationService.getAllReservations());
        return "reservations";
    }

}
