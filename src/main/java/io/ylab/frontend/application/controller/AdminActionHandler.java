package io.ylab.frontend.application.controller;

import io.ylab.common.response.Response;
import io.ylab.frontend.infrastructure.console.ConsoleHelper;
import io.ylab.frontend.presentation.view.DefaultMenuView;

/**
 * Обработчик действий для роли администратора.
 * Отображает меню администратора и  обрабатывает выбор пользователя.
 */
public class AdminActionHandler extends DefaultMenuActionHandler {
    /**
     * Помощник для работы с консолью.
     */
    private final ConsoleHelper console;

    /**
     * Конструктор.
     *
     * @param view    Представление меню.
     * @param console Помощник для работы с консолью.
     */
    public AdminActionHandler(DefaultMenuView view, ConsoleHelper console) {
        super(view);
        this.console = console;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response handleAndGetState() {
        return null;
    }
}
