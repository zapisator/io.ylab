package io.ylab.auth.domain.service;

import io.ylab.auth.domain.model.User;

/**
 * Сервис для аутентификации пользователей.
 */
public interface AuthenticationService {

    /**
     * Проверяет,  правильны ли  указанные  имя пользователя и  пароль.
     *
     * @param username Имя пользователя.
     * @param password Пароль.
     * @return true,  если аутентификация прошла успешно,  иначе false.
     */
    boolean authenticate(String username, String password);

    /**
     * Возвращает текущего аутентифицированного пользователя.
     *
     * @return Текущий пользователь,  или null,  если пользователь не аутентифицирован.
     */
    User getCurrentUser();

    /**
     * Устанавливает текущего аутентифицированного пользователя.
     *
     * @param user Пользователь для установки.
     */
    void setCurrentUser(User user);
}
