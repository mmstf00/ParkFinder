package com.parkfinder.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/directions")
public class DirectionsController {

    @GetMapping
    public String getDirections() {
        return "directions";
    }
}
