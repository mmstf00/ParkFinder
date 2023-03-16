package com.parkfinder.controller.mvc;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DirectionsControllerTest {

    private final DirectionsController controller = new DirectionsController();

    @Test
    void testDirectionsPage() {
        String directionsPage = controller.getDirections();
        assertEquals("directions", directionsPage);
    }
}