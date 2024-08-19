package io.ylab.backend.domain.service.search;

import java.util.List;

/**
 * Интерфейс для репозиториев, поддерживающих спецификации (фильтрацию).
 *
 * @param <T> Тип сущности.
 */
public interface SpecificationService<T> {
    /**
     * Находит все сущности, удовлетворяющие заданной спецификации.
     *
     * @param specification Спецификация (условие фильтрации).
     * @return Список сущностей, удовлетворяющих спецификации.
     */
    List<T> findAll(Specification<T> specification);
}
