package io.ylab.backend.domain.service.search;

import io.ylab.common.request.filter.FilterBuilder;
import io.ylab.common.request.filter.FilterCriterion;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ReflectiveSpecification<T> implements Specification<T> {
    private final Predicate<T> predicate;

    @Override
    public List<T> apply(List<T> entities) {
        return entities.stream()
                .filter(predicate)
                .collect(Collectors.toList());
    }

    public static <R> ReflectiveSpecification<R> of(
            Class<R> tClass,
            List<FilterCriterion<?>> criteria
    ) {
        final Predicate<R> predicate = new FilterBuilder<>(tClass)
                .buildFilter(criteria);

        return new ReflectiveSpecification<>(predicate);
    }
}