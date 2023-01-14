package com.parkfinder.controller.mvc;

import com.parkfinder.service.MarkerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    private final MarkerService markerService;

    public HomeController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @RequestMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("markers", markerService.getMarkers());
        return "index";
    }
}
