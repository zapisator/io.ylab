package io.ylab.frontend.application.controller;

import io.ylab.common.response.Response;
import io.ylab.frontend.infrastructure.console.ConsoleHelper;
import io.ylab.frontend.presentation.view.DefaultMenuView;

/**
 * Обработчик действий для роли менеджера.
 * Отображает меню менеджера и  обрабатывает выбор пользователя.
 */
public class ManagerActionHandler extends DefaultMenuActionHandler {
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
    public ManagerActionHandler(DefaultMenuView view, ConsoleHelper console) {
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
