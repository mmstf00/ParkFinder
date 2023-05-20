package com.parkfinder.model.paging;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PagedTest {
    @Test
    void getPage_ReturnsPage() {
        // Arrange
        List<Integer> content = Arrays.asList(1, 2, 3);
        int totalPages = 2;
        Page<Integer> page = new Page<>(content, totalPages);
        Paging paging = new Paging();

        Paged<Integer> paged = new Paged<>(page, paging);

        // Act
        Page<Integer> result = paged.getPage();

        // Assert
        assertEquals(page, result);
    }

    @Test
    void getPaging_ReturnsPaging() {
        // Arrange
        List<Integer> content = new ArrayList<>();
        int totalPages = 5;
        Page<Integer> page = new Page<>(content, totalPages);

        Paging paging = new Paging(false, true, 10, 3, new ArrayList<>());

        Paged<Integer> paged = new Paged<>(page, paging);

        // Act
        Paging result = paged.getPaging();

        // Assert
        assertEquals(paging, result);
    }
}