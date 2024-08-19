package io.ylab.storage.in.memory.storage;

import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;

import java.util.List;
import java.util.Optional;

public interface Storage<T, I> {
    List<T> findAll();

    List<T> findAll(Sorting sorting);

    List<T> findAll(Pagination pagination);

    List<T> findAll(Sorting sorting, Pagination pagination);

    Optional<T> findById(I id);

    I save(T entity);

    void deleteById(I id);
}
