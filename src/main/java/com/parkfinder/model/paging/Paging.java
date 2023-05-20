package com.parkfinder.model.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Paging {

    public static final int PAGINATION_STEP = 3;

    private boolean nextEnabled;
    private boolean prevEnabled;
    private int pageSize;
    private int pageNumber;

    private List<PageItem> items = new ArrayList<>();

    /**
     * Adds page items to the pagination.
     *
     * @param from       The starting index of the page items.
     * @param to         The ending index of the page items.
     * @param pageNumber The current page number.
     */
    public void addPageItems(int from, int to, int pageNumber) {
        for (int i = from; i < to; i++) {
            items.add(PageItem.builder()
                    .active(pageNumber != i)
                    .index(i)
                    .pageItemType(PageItemType.PAGE)
                    .build());
        }
    }

    /**
     * Adds the last page item to the pagination.
     *
     * @param pageSize The total number of pages.
     */
    public void last(int pageSize) {
        items.add(PageItem.builder()
                .active(false)
                .pageItemType(PageItemType.DOTS)
                .build());

        items.add(PageItem.builder()
                .active(true)
                .index(pageSize)
                .pageItemType(PageItemType.PAGE)
                .build());
    }

    /**
     * Adds the first page item to the pagination.
     *
     * @param pageNumber The current page number.
     */
    public void first(int pageNumber) {
        items.add(PageItem.builder()
                .active(pageNumber != 1)
                .index(1)
                .pageItemType(PageItemType.PAGE)
                .build());

        items.add(PageItem.builder()
                .active(false)
                .pageItemType(PageItemType.DOTS)
                .build());
    }

    /**
     * Generates a pagination object based on the total number of pages,
     * current page number, and page size.
     *
     * @param totalPages The total number of pages.
     * @param pageNumber The current page number.
     * @param pageSize   The number of items per page.
     * @return A Paging object representing the pagination state.
     */
    public static Paging of(int totalPages, int pageNumber, int pageSize) {
        Paging paging = new Paging();
        paging.setPageSize(pageSize);
        paging.setNextEnabled(pageNumber != totalPages);
        paging.setPrevEnabled(pageNumber != 1);
        paging.setPageNumber(pageNumber);

        if (totalPages < PAGINATION_STEP * 2 + 6) {
            paging.addPageItems(1, totalPages + 1, pageNumber);

        } else if (pageNumber < PAGINATION_STEP * 2 + 1) {
            paging.addPageItems(1, PAGINATION_STEP * 2 + 4, pageNumber);
            paging.last(totalPages);

        } else if (pageNumber > totalPages - PAGINATION_STEP * 2) {
            paging.first(pageNumber);
            paging.addPageItems(totalPages - PAGINATION_STEP * 2 - 2, totalPages + 1, pageNumber);

        } else {
            paging.first(pageNumber);
            paging.addPageItems(pageNumber - PAGINATION_STEP, pageNumber + PAGINATION_STEP + 1, pageNumber);
            paging.last(totalPages);
        }

        return paging;
    }
}
