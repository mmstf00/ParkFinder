package com.parkfinder.model.paging;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PageTest {
    @Test
    void getContent_ReturnsContent() {
        // Arrange
        List<Integer> content = Arrays.asList(1, 2, 3);
        int totalPages = 2;
        Page<Integer> page = new Page<>(content, totalPages);

        // Act
        List<Integer> result = page.getContent();

        // Assert
        assertEquals(content, result);
    }

    @Test
    void getTotalPages_ReturnsTotalPages() {
        // Arrange
        List<Integer> content = new ArrayList<>();
        int totalPages = 5;
        Page<Integer> page = new Page<>(content, totalPages);

        // Act
        int result = page.getTotalPages();

        // Assert
        assertEquals(totalPages, result);
    }
}