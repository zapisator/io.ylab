package io.ylab.backend.domain.service.search;

import java.util.List;

/**
 * Интерфейс, представляющий спецификацию (условие фильтрации) для запросов.
 *
 * @param <T> Тип сущности, к которой применяется спецификация.
 */
public interface Specification<T> {
    /**
     * Применяет спецификацию к заданному списку сущностей.
     *
     * @param entities Список сущностей.
     * @return Список сущностей, удовлетворяющих спецификации.
     */
    List<T> apply(List<T> entities);
}
