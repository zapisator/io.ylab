package io.ylab.frontend.presentation.auth;

import io.ylab.auth.domain.model.User;

/**
 * Интерфейс для управления аутентификацией пользователя во frontend-е.
 */
public interface AuthenticationManager {
    /**
     * Выполняет вход пользователя с  указанным  именем пользователя и  паролем.
     *
     * @param username Имя пользователя.
     * @param password Пароль.
     * @return true,  если вход выполнен успешно,  иначе false.
     */
    boolean login(String username, String password);
    /**
     * Выполняет выход пользователя.
     */
    void logout();
    /**
     * Возвращает текущего аутентифицированного пользователя.
     *
     * @return Текущий пользователь.
     */
    User getCurrentUser();
    /**
     * Возвращает токен доступа текущего пользователя.
     *
     * @return Токен доступа.
     */
    String getToken();
    /**
     * Обновляет токен доступа текущего пользователя.
     */
    void refreshToken();
    /**
     * Проверяет,  аутентифицирован ли  текущий пользователь.
     *
     * @return true,  если пользователь аутентифицирован,  иначе false.
     */
    boolean isLoggedIn();
}
