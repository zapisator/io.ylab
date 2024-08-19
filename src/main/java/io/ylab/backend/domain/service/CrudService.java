package io.ylab.backend.domain.service;

import java.util.Optional;

public interface CrudService<T, I> {

    Optional<T> findById(I id);

    T create(T entity);

    T update(T entity);

    void deleteById(I id);
}
