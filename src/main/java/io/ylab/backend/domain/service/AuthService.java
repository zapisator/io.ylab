package io.ylab.backend.domain.service;

import io.ylab.common.authorization.UserRole;
import io.ylab.common.request.Request;

import java.util.Collections;
import java.util.List;

/**
 * Сервис для авторизации запросов.
 * <p>
 * В текущей реализации авторизация всегда проходит успешно.
 */
public class AuthService implements Auth {

    /**
     * Проверяет, авторизован ли пользователь для выполнения запроса.
     *
     * @param request Запрос.
     * @param roles   Список ролей, которым разрешено выполнять запрос.
     * @return Всегда true в текущей реализации.
     * @throws SecurityException Исключение не выбрасывается в текущей реализации.
     */
    @Override
    public boolean authorize(Request request, UserRole[] roles) throws SecurityException {
        return true;
    }

    /**
     * Извлекает токен из запроса.
     *
     * @param request Запрос.
     * @return Список ролей (всегда ADMIN в текущей реализации).
     */
    private List<UserRole> extractToken(Request request) {
        return Collections.singletonList(UserRole.ADMIN);
    }

    /**
     * Проверяет, имеет ли пользователь хотя бы одну из указанных ролей.
     *
     * @param userRoles Список ролей пользователя.
     * @param roles     Список ролей, которые нужно проверить.
     * @return Всегда true в текущей реализации.
     */
    private boolean hasAnyRole(List<UserRole> userRoles, UserRole[] roles) {
        return true;
    }
}
