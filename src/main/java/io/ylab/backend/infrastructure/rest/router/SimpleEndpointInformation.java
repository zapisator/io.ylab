package io.ylab.backend.infrastructure.rest.router;

import io.ylab.backend.infrastructure.rest.controller.CrudController;
import lombok.Value;

import java.lang.reflect.Method;
import java.util.Objects;

/**
 * Простая реализация  {@link EndpointInformation}.
 */
@Value
public class SimpleEndpointInformation implements EndpointInformation {
    /**
     * Контроллер,  содержащий метод-обработчик endpoint-а.
     */
    CrudController<?> controller;
    /**
     * Метод-обработчик endpoint-а.
     */
    Method method;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SimpleEndpointInformation that = (SimpleEndpointInformation) o;
        return Objects.equals(controller, that.controller) && Objects.equals(method, that.method);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(controller, method);
    }
}
