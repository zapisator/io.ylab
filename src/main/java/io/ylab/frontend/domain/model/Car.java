package io.ylab.frontend.domain.model;

import io.ylab.backend.domain.model.CarStatus;
import lombok.Builder;
import lombok.Value;

/**
 * Модель автомобиля.
 */
@Value
@Builder
public class Car {
    /**
     * Уникальный идентификатор автомобиля.
     */
    Long id;
    /**
     * Марка автомобиля.
     */
    String brand;
    /**
     * Модель автомобиля.
     */
    String model;
    /**
     * Год выпуска автомобиля.
     */
    int year;
    /**
     * Цена автомобиля.
     */
    double price;
    /**
     * Статус автомобиля.
     */
    CarStatus status;
}
