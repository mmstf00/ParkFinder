package com.parkfinder.model.paging;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Paged<T> {

    private Page<T> page;

    private Paging paging;

}
