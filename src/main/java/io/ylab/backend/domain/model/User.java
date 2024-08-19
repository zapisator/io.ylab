package io.ylab.backend.domain.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Value;

/**
 * Модель пользователя.
 */
@Value
@Builder
public class User {

    /**
     * Уникальный идентификатор пользователя.
     */
    @EqualsAndHashCode.Exclude
    Long id;

    /**
     * Имя пользователя.
     */
    String firstName;

    /**
     * Фамилия пользователя.
     */
    String lastName;

    /**
     * Электронная почта пользователя.
     */
    String email;

    /**
     * Номер телефона пользователя.
     */
    String phoneNumber;

    /**
     * Адрес пользователя.
     */
    String address;
}
