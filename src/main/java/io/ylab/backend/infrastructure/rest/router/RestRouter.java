package io.ylab.backend.infrastructure.rest.router;

import io.ylab.common.request.Request;
import io.ylab.common.response.Response;

import java.util.Map;

/**
 * Реализация роутера REST API,  которая использует  {@link Map}
 * для сопоставления запросов с  соответствующими endpoint-ами.
 */
public interface RestRouter {
    /**
     * Регистрирует endpoint в роутере.
     *
     * @param definition Определение endpoint'а,  включая метод,  путь и роли.
     * @param information Метод-обработчик endpoint'а и контроллер,  содержащий метод-обработчик.
     */
    void registerEndpoint(EndpointDefinition definition, EndpointInformation information);

    /**
     * Обрабатывает запрос и возвращает ответ.
     *
     * @param request Запрос.
     * @return Ответ.
     */
    Response handleRequest(Request request);
}
