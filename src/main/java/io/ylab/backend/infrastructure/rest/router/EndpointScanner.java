package io.ylab.backend.infrastructure.rest.router;

import io.ylab.backend.infrastructure.rest.controller.CrudController;
import io.ylab.common.annotation.Endpoint;

/**
 * Интерфейс для сканеров endpoint-ов,  которые сканируют классы контроллеров
 * на наличие аннотаций {@link Endpoint} и  регистрируют найденные endpoint-ы в роутере.
 */
public interface EndpointScanner {

    /**
     * Сканирует классы на наличие аннотаций {@link Endpoint} и регистрирует найденные endpoint'ы в роутере.
     *
     * @param controllerClasses Массив классов контроллеров,  которые нужно сканировать.
     */
    void scan(CrudController<?>... controllerClasses);
}
