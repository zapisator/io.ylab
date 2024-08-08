package io.ylab.common.request;

/**
 * Функциональный  интерфейс  для  валидации  значений.
 *
 * @param <T> Тип  значения  для  валидации.
 */
@FunctionalInterface
interface Validator<T> {
    boolean isValid(T value);
}
