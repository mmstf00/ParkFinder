package com.parkfinder.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class AccessDeniedController {
    @GetMapping("/accessDenied")
    public ModelAndView accessDeniedPage() {
        return new ModelAndView("access-denied");
    }
}
