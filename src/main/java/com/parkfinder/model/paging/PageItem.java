package com.parkfinder.model.paging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PageItem {

    private PageItemType pageItemType;

    private int index;

    private boolean active;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PageItem pageItem = (PageItem) o;
        return index == pageItem.index &&
                active == pageItem.active &&
                pageItemType == pageItem.pageItemType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageItemType, index, active);
    }
}