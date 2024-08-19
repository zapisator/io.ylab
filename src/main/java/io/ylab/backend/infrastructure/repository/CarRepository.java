package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Car;
import io.ylab.backend.infrastructure.repository.common.JpaRepository;

/**
 * Интерфейс репозитория для работы с автомобилями.
 */
public interface CarRepository extends JpaRepository<Car, Long> {

}
