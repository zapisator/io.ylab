package io.ylab.backend.infrastructure.rest.router;

import io.ylab.backend.infrastructure.rest.controller.CrudController;

import java.lang.reflect.Method;

/**
 * Интерфейс,  представляющий информацию об endpoint-е,  необходимую для его обработки.
 */
public interface EndpointInformation {
    /**
     * Возвращает контроллер,  содержащий метод-обработчик endpoint-а.
     *
     * @return Контроллер.
     */
    CrudController<?> getController();
    /**
     * Возвращает метод-обработчик endpoint-а.
     *
     * @return Метод.
     */
    Method getMethod();
}
