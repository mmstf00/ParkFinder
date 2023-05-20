package com.parkfinder.model.paging;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PagingTest {

    @Test
    void addPageItems_AddsPageItemsToList() {
        // Arrange
        Paging paging = new Paging();
        int from = 1;
        int to = 5;
        int pageNumber = 2;

        // Act
        paging.addPageItems(from, to, pageNumber);
        List<PageItem> items = paging.getItems();

        // Assert
        assertEquals(to - from, items.size());
        for (int i = from; i < to; i++) {
            PageItem expected = PageItem.builder()
                    .active(pageNumber != i)
                    .index(i)
                    .pageItemType(PageItemType.PAGE)
                    .build();
            assertTrue(items.contains(expected));
        }
    }

    @Test
    void last_AddsLastPageItemToList() {
        // Arrange
        Paging paging = new Paging();
        int pageSize = 5;

        // Act
        paging.last(pageSize);
        List<PageItem> items = paging.getItems();

        // Assert
        assertEquals(2, items.size());
        PageItem dots = PageItem.builder()
                .active(false)
                .pageItemType(PageItemType.DOTS)
                .build();
        PageItem last = PageItem.builder()
                .active(true)
                .index(pageSize)
                .pageItemType(PageItemType.PAGE)
                .build();
        assertTrue(items.contains(dots));
        assertTrue(items.contains(last));
    }

    @Test
    void first_AddsFirstPageItemToList() {
        // Arrange
        Paging paging = new Paging();
        int pageNumber = 2;

        // Act
        paging.first(pageNumber);
        List<PageItem> items = paging.getItems();

        // Assert
        assertEquals(2, items.size());
        PageItem first = PageItem.builder()
                .active(true)
                .index(1)
                .pageItemType(PageItemType.PAGE)
                .build();
        PageItem dots = PageItem.builder()
                .active(false)
                .pageItemType(PageItemType.DOTS)
                .build();
        assertTrue(items.contains(first));
        assertTrue(items.contains(dots));
    }

    @Test
    void of_GeneratesPagingObject() {
        // Case 1: totalPages < PAGINATION_STEP * 2 + 6
        int totalPagesCase1 = 6;
        int pageNumberCase1 = 3;
        int pageSizeCase1 = 10;

        Paging pagingCase1 = Paging.of(totalPagesCase1, pageNumberCase1, pageSizeCase1);
        assertEquals(pageSizeCase1, pagingCase1.getPageSize());
        assertEquals(pageNumberCase1, pagingCase1.getPageNumber());
        assertEquals(totalPagesCase1, pagingCase1.getItems().size());
        assertTrue(pagingCase1.isNextEnabled());
        assertTrue(pagingCase1.isPrevEnabled());

        // Case 2: pageNumber < PAGINATION_STEP * 2 + 1
        int totalPagesCase2 = 10;
        int pageNumberCase2 = 2;
        int pageSizeCase2 = 10;

        Paging pagingCase2 = Paging.of(totalPagesCase2, pageNumberCase2, pageSizeCase2);
        assertEquals(pageSizeCase2, pagingCase2.getPageSize());
        assertEquals(pageNumberCase2, pagingCase2.getPageNumber());
        assertEquals(Paging.PAGINATION_STEP * 2 + 4, pagingCase2.getItems().size());
        assertTrue(pagingCase2.isNextEnabled());
        assertTrue(pagingCase2.isPrevEnabled());

        // Case 3: pageNumber > totalPages - PAGINATION_STEP * 2
        int totalPagesCase3 = 10;
        int pageNumberCase3 = 9;
        int pageSizeCase3 = 10;

        Paging pagingCase3 = Paging.of(totalPagesCase3, pageNumberCase3, pageSizeCase3);
        assertEquals(pageSizeCase3, pagingCase3.getPageSize());
        assertEquals(pageNumberCase3, pagingCase3.getPageNumber());
        assertEquals(Paging.PAGINATION_STEP * 2 + 4, pagingCase3.getItems().size());
        assertTrue(pagingCase3.isNextEnabled());
        assertTrue(pagingCase3.isPrevEnabled());

        // Case 4: Default case
        int totalPagesCase4 = 20;
        int pageNumberCase4 = 15;
        int pageSizeCase4 = 10;

        Paging pagingCase4 = Paging.of(totalPagesCase4, pageNumberCase4, pageSizeCase4);
        assertEquals(pageSizeCase4, pagingCase4.getPageSize());
        assertEquals(pageNumberCase4, pagingCase4.getPageNumber());
        assertEquals(Paging.PAGINATION_STEP * 2 + 5, pagingCase4.getItems().size());
        assertTrue(pagingCase4.isNextEnabled());
        assertTrue(pagingCase4.isPrevEnabled());
    }
}