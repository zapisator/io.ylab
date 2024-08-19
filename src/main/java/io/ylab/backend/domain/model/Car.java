package io.ylab.backend.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
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
    @EqualsAndHashCode.Exclude
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
