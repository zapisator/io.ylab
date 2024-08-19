package io.ylab.backend.domain.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

/**
 * Модель записи в журнале действий.
 */
@Value
@Builder
public class Log {

    /**
     * Уникальный идентификатор записи.
     */
    Long id;

    /**
     * Имя пользователя,  выполнившего действие.
     */
    String user;

    /**
     * Тип действия (например,  "CREATE_USER",  "UPDATE_ORDER",  "LOGIN").
     */
    String action;

    /**
     * Дополнительная информация о действии.
     */
    String details;

    /**
     * Время выполнения действия.
     */
    LocalDateTime timestamp;
}
