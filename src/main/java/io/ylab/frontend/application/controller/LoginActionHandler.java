package io.ylab.frontend.application.controller;

import io.ylab.common.authorization.UserRole;
import io.ylab.common.response.DefaultResponse;
import io.ylab.common.response.Response;
import io.ylab.frontend.domain.model.Session;
import io.ylab.frontend.infrastructure.console.ConsoleHelper;
import io.ylab.frontend.presentation.auth.AuthenticationManager;
import io.ylab.frontend.presentation.view.DefaultMenuView;

/**
 * Обработчик действий для страницы входа.
 * Отображает меню входа/регистрации и  обрабатывает выбор пользователя.
 */
public class LoginActionHandler extends DefaultMenuActionHandler {
    /**
     * Менеджер аутентификации.
     */
    private final AuthenticationManager authenticationManager;
    /**
     * Помощник для работы с консолью.
     */
    private final ConsoleHelper helper;

    /**
     * Конструктор.
     *
     * @param view                Представление меню.
     * @param helper              Помощник для работы с консолью.
     * @param authenticationManager Менеджер аутентификации.
     */
    public LoginActionHandler(
            DefaultMenuView view, ConsoleHelper helper,
            AuthenticationManager authenticationManager
    ) {
        super(view);
        this.helper = helper;
        this.authenticationManager = authenticationManager;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<Session> handleAndGetState() {
        helper.writeLine(view.toString());
        final String option = helper.readLine();
        Session session = null;
        if ("2".equals(option)) {
            helper.writeLine("See you!");
            System.exit(0);
        } else if ("0".equals(option)) {
            session = handleLogin();
        } else if ("1".equals(option)) {
            session = handleRegistration();
        } else {
            helper.writeLine("Invalid option selected.");
        }
        return DefaultResponse.<Session>builder()
                .data(session)
                .build();
    }

    /**
     * Обрабатывает действие входа пользователя.
     *
     * @return Сессию пользователя в случае успешного входа,  иначе null.
     */
    private Session handleLogin() {
        helper.writeLine("Enter username:");
        String username = helper.readLine();
        helper.writeLine("Enter password:");
        String password = helper.readLine();

        //  Используем  AuthenticationManager  для  проверки  логина  и  пароля
        if (authenticationManager.login(username, password)) {
            //  Успешная  аутентификация
            UserRole role = authenticationManager.getCurrentUser().getRoles().get(0); //  Получаем  роль  пользователя
            helper.writeLine("Login successful! Welcome, " + username + "!");
            return Session.builder()
                    .user(authenticationManager.getCurrentUser()) //  Получаем  информацию  о  пользователе
                    .token(authenticationManager.getToken()) //  Получаем  токен
                    .role(role) //  Устанавливаем  роль
                    .expirationTime(null) // TODO:  Обработка  времени  истечения  токена
                    .build();
        } else {
            //  Ошибка  аутентификации
            helper.writeLine("Invalid username or password.");
            return null;
        }
    }

    /**
     * Обрабатывает действие регистрации пользователя.
     *
     * @return Сессию пользователя в случае успешной регистрации,  иначе null.
     */
    private Session handleRegistration() {
        helper.writeLine("Registration is not implemented yet.");
        return null;
    }
}
