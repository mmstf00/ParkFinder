package com.parkfinder.model.paging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;

class PageItemTest {
    @Test
    void pageItem_GettersAndSetters_ReturnCorrectValues() {
        // Arrange
        PageItemType pageItemType = PageItemType.PAGE;
        int index = 1;
        boolean active = true;

        // Act
        PageItem pageItem = new PageItem();
        pageItem.setPageItemType(pageItemType);
        pageItem.setIndex(index);
        pageItem.setActive(active);

        // Assert
        assertEquals(pageItemType, pageItem.getPageItemType());
        assertEquals(index, pageItem.getIndex());
        assertEquals(active, pageItem.isActive());
    }

    @Test
    void pageItem_Builder_ReturnCorrectValues() {
        // Arrange
        PageItemType pageItemType = PageItemType.DOTS;
        int index = 2;
        boolean active = false;

        // Act
        PageItem pageItem = PageItem.builder()
                .pageItemType(pageItemType)
                .index(index)
                .active(active)
                .build();

        // Assert
        assertEquals(pageItemType, pageItem.getPageItemType());
        assertEquals(index, pageItem.getIndex());
        assertEquals(active, pageItem.isActive());
    }

    @Test
    void pageItem_AllArgsConstructor_ReturnCorrectValues() {
        // Arrange
        PageItemType pageItemType = PageItemType.PAGE;
        int index = 3;
        boolean active = true;

        // Act
        PageItem pageItem = new PageItem(pageItemType, index, active);

        // Assert
        assertEquals(pageItemType, pageItem.getPageItemType());
        assertEquals(index, pageItem.getIndex());
        assertEquals(active, pageItem.isActive());
    }

    @Test
    void pageItem_NoArgsConstructor_ReturnDefaultValues() {
        // Arrange
        PageItem pageItem = new PageItem();

        // Assert
        assertNull(pageItem.getPageItemType());
        assertEquals(0, pageItem.getIndex());
        assertFalse(pageItem.isActive());
    }
}