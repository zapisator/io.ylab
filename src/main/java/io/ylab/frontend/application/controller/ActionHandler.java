package io.ylab.frontend.application.controller;

import io.ylab.common.response.Response;

/**
 * Интерфейс для обработчиков действий пользователя во frontend-е.
 */
public interface ActionHandler {
    /**
     * Обрабатывает действие пользователя и  возвращает новое состояние приложения.
     *
     * @return Ответ,  содержащий информацию о новом состоянии приложения.
     */
    Response handleAndGetState();
}
