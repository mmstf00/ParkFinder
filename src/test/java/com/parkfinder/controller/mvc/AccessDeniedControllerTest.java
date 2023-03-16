package com.parkfinder.controller.mvc;

import org.junit.jupiter.api.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccessDeniedControllerTest {

    private final AccessDeniedController controller = new AccessDeniedController();

    @Test
    void testAccessDeniedPage() {
        ModelAndView modelAndView = controller.accessDeniedPage();
        assertEquals("access-denied", modelAndView.getViewName());
    }
}