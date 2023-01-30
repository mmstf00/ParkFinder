package com.parkfinder.controller.mvc;

import com.parkfinder.service.MarkerService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
public class HomeController {

    private static final String INDEX_PAGE = "index";
    private final MarkerService markerService;

    public HomeController(MarkerService markerService) {
        this.markerService = markerService;
    }

    @RequestMapping("/")
    public String indexPage(Model model) {
        model.addAttribute("markers", markerService.getMarkers());
        return INDEX_PAGE;
    }

    @GetMapping("/search")
    public String performSearch(
            Model model,
            @RequestParam("dateFrom") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateFrom,
            @RequestParam("dateTo") @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime dateTo) {
        model.addAttribute("markers",
                markerService.getAllBySelectedDateRange(dateFrom, dateTo));
        return INDEX_PAGE;
    }
}
