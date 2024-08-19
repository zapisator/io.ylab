package io.ylab.backend.infrastructure.rest.router;

import io.ylab.common.authorization.UserRole;
import io.ylab.common.method.MethodType;
import lombok.Value;

import java.util.Objects;

/**
 * Простая реализация  {@link EndpointDefinition}.
 */
@Value
public class SimpleEndpointDefinition implements EndpointDefinition {
    /**
     * HTTP-метод endpoint-а.
     */
    MethodType method;
    /**
     * Путь endpoint-а.
     */
    String path;
    /**
     * Список ролей,  которым разрешен доступ к endpoint-у.
     */
    UserRole[] roles;
    /**
     * Флаг,  указывающий,  является ли  путь endpoint-а шаблоном.
     */
    boolean isTemplate;

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
        final SimpleEndpointDefinition that = (SimpleEndpointDefinition) o;
        return method == that.method && Objects.equals(path, that.path);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
