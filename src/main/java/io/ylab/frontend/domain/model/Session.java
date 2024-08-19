package io.ylab.frontend.domain.model;

import io.ylab.common.authorization.UserRole;
import lombok.Builder;
import lombok.Value;
import io.ylab.auth.domain.model.User;


import java.time.LocalDateTime;

/**
 * Модель сессии пользователя во frontend-е.
 */
@Value
@Builder
public class Session {
    /**
     * Пользователь,  связанный с сессией.
     */
    User user;
    /**
     * Токен доступа для сессии.
     */
    String token;
    /**
     * Роль пользователя в сессии.
     */
    UserRole role;
    /**
     * Время истечения срока действия сессии.
     */
    LocalDateTime expirationTime;
}