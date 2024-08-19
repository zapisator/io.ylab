package io.ylab.frontend.application.controller;

import io.ylab.common.response.Response;
import io.ylab.frontend.presentation.view.DefaultMenuView;
import lombok.RequiredArgsConstructor;

/**
 * Обработчик действий для представления меню.
 */
@RequiredArgsConstructor
public class DefaultMenuActionHandler implements ActionHandler {
    /**
     * Представление меню.
     */
    protected final DefaultMenuView view;

    /**
     * {@inheritDoc}
     */
    @Override
    public Response handleAndGetState() {
        return null;
    }
}