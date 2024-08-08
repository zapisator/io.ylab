package io.ylab.frontend.domain.model;

import io.ylab.backend.domain.model.OrderStatus;
import io.ylab.backend.domain.model.OrderType;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Модель заказа.
 */
@Value
@Builder
public class Order {
    /**
     * Уникальный идентификатор заказа.
     */
    Long id;
    /**
     * Идентификатор пользователя,  который создал заказ.
     */
    Long userId;
    /**
     * Идентификатор автомобиля,  связанного с заказом.
     */
    Long carId;
    /**
     * Количество товаров в заказе.
     */
    int quantity;
    /**
     * Статус заказа.
     */
    OrderStatus status;
    /**
     * Тип заказа.
     */
    OrderType type;
    /**
     * Время создания заказа.
     */
    LocalDateTime createdAt;
    /**
     * Время последнего обновления заказа.
     */
    LocalDateTime updatedAt;
}
