package com.parkfinder.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserLoginController {

    private static final String LOGIN_FORM = "login";

    @GetMapping("/login")
    public String login() {
        return LOGIN_FORM;
    }
}
