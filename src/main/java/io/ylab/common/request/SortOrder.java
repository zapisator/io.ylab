package io.ylab.common.request;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter
@Accessors(fluent = true)
@RequiredArgsConstructor
public enum SortOrder {
    ASC("asc"),
    DESC("desc");

    private final String key;
}
