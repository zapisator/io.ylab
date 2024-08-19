package io.ylab.backend.infrastructure.repository.common;

/**
 * Интерфейс для репозиториев JPA (Java Persistence API), предоставляющих дополнительные операции,
 * специфичные для JPA.
 *
 * @param <T>  Тип сущности.
 * @param <I> Тип идентификатора сущности.
 */
public interface JpaRepository<T, I> extends PagingAndSortingRepository<T, I>{
}
