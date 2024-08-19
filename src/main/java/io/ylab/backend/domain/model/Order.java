package io.ylab.backend.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
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
    @EqualsAndHashCode.Exclude
    Long id;

    /**
     * Идентификатор пользователя,  создавшего  заказ.
     */
    Long userId;

    /**
     * Идентификатор автомобиля,  включенного  в  заказ.
     */
    Long carId;

    /**
     * Количество автомобилей в заказе.
     */
    int quantity;

    /**
     * Статус заказа.
     */
    OrderStatus status;

    /**
     * Тип заказа (покупка или обслуживание).
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
