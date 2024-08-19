package io.ylab.common.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum RequestParamKey {
    PAGE("page"),
    PAGE_SIZE("pageSize"),
    SORT_BY("sortBy"),
    SORT_ORDER("sortOrder"),
    SEARCH("search");

    private final String key;
}
