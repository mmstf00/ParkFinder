package com.parkfinder.controller.mvc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserLoginControllerTest {

    private final UserLoginController controller = new UserLoginController();

    @Test
    void testAccessDeniedPage() {
        String loginPage = controller.login();
        assertEquals("login", loginPage);
    }

}