package io.ylab.backend.domain.service;

import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.filter.FilterCriterion;

import java.util.List;

public interface FindAllWithPaginationSortingAndSpecificationService<T, I> extends CrudService<T, I> {
    List<T> findAll(Pagination pagination, Sorting sorting, List<FilterCriterion<?>> criteria);
}
