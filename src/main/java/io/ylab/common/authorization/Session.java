package io.ylab.common.authorization;

import lombok.Value;

/**
 * Класс,  представляющий сессию пользователя.
 */
@Value
public class Session {
    /**
     * Идентификатор пользователя.
     */
    String userId;
    /**
     * Токен доступа.
     */
    String token;
}
