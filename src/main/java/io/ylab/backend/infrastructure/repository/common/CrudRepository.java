package io.ylab.backend.infrastructure.repository.common;

import java.util.List;
import java.util.Optional;

/**
 * Интерфейс для репозиториев, предоставляющих базовые CRUD-операции.
 *
 * @param <T>  Тип сущности.
 * @param <I> Тип идентификатора сущности.
 */
public interface CrudRepository<T, I> {
    List<T> findAll();

    Optional<T> findById(I id);

    T save(T entity);

    void deleteById(I id);
}
