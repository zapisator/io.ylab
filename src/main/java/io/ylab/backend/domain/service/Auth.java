package io.ylab.backend.domain.service;

import io.ylab.common.authorization.UserRole;
import io.ylab.common.request.Request;

/**
 * Интерфейс для сервисов авторизации.
 */
public interface Auth {

    /**
     * Проверяет, авторизован ли пользователь для выполнения запроса.
     *
     * @param request Запрос.
     * @param roles   Список ролей, которым разрешено выполнять запрос.
     * @return true, если пользователь авторизован, иначе false.
     * @throws SecurityException Если пользователь не авторизован.
     */
    boolean authorize(Request request, UserRole[] roles) throws SecurityException;
}
