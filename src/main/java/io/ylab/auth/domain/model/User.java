package io.ylab.auth.domain.model;

import io.ylab.common.authorization.UserRole;
import lombok.Value;

import java.util.List;

/**
 * Модель пользователя в системе аутентификации.
 */
@Value
public class User {

    /**
     * Имя пользователя.
     */
    String username;

    /**
     * Хеш пароля пользователя.
     */
    String passwordHash;

    /**
     * Список ролей пользователя.
     */
    List<UserRole> roles;
}
