package io.ylab.backend.domain.service;

import io.ylab.backend.domain.model.Log;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.backend.domain.service.search.ReflectiveSpecification;
import io.ylab.backend.domain.service.search.Specification;
import io.ylab.backend.infrastructure.repository.LogRepository;
import io.ylab.common.request.filter.FilterCriterion;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class LogService implements FindAllWithPaginationSortingAndSpecificationService<Log, Long> {
    private final LogRepository logRepository;

    @Override
    public List<Log> findAll(
            Pagination pagination, Sorting sorting,
            List<FilterCriterion<?>> criteria
    ) {
        final List<Log> cars = logRepository.findAll(pagination, sorting);
        final Specification<Log> carSpecification = ReflectiveSpecification.of(Log.class, criteria);
        return carSpecification.apply(cars);
    }

    @Override
    public Optional<Log> findById(Long id) {
        return logRepository.findById(id);
    }

    @Override
    public Log create(Log log) {
        return logRepository.save(log);
    }

    @Override
    public Log update(Log log) {
        if (!logRepository.findById(log.getId()).isPresent()) {
            throw new IllegalArgumentException("Log with ID " + log.getId() + " not found");
        }

        return logRepository.save(log);
    }

    @Override
    public void deleteById(Long id) {
        logRepository.deleteById(id);
    }
}