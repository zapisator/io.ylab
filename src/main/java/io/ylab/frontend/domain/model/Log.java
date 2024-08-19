package io.ylab.frontend.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Модель записи лога.
 */
@Value
@Builder
public class Log {
    /**
     * Уникальный идентификатор записи лога.
     */
    Long id;
    /**
     * Имя пользователя,  связанного с записью лога.
     */
    String user;
    /**
     * Действие,  записанное в логе.
     */
    String action;
    /**
     * Детали действия,  записанного в логе.
     */
    String details;
    /**
     * Время создания записи лога.
     */
    LocalDateTime timestamp;
}
