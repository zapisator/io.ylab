package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Car;

import java.util.List;

/**
 * Интерфейс репозитория для работы с автомобилями.
 */
public interface CarRepository {

    /**
     * Возвращает список всех автомобилей.
     *
     * @return Список всех автомобилей.
     */
    List<Car> findAll();
}
