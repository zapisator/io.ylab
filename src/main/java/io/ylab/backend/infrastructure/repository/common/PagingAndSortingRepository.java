package io.ylab.backend.infrastructure.repository.common;

import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;

import java.util.List;

/**
 * Интерфейс для репозиториев, поддерживающих пагинацию и сортировку.
 *
 * @param <T> Тип сущности.
 */
public interface PagingAndSortingRepository<T, I> extends CrudRepository<T, I>{
    /**
     * Находит все сущности с пагинацией и сортировкой.
     *
     * @param pagination Параметры пагинации.
     * @param sorting     Параметры сортировки.
     * @return Список сущностей с пагинацией и сортировкой.
     */
    List<T> findAll(Pagination pagination, Sorting sorting);
}
