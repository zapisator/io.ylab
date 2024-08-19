package io.ylab.backend.domain.service.dto;

import io.ylab.common.request.SortOrder;
import lombok.Value;

@Value
public class Sorting {
    String sortBy;
    SortOrder sortOrder;
}
