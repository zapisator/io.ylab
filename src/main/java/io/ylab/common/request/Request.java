package io.ylab.common.request;

import io.ylab.common.method.MethodType;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.nio.file.Path;
import java.util.Map;
import java.util.Objects;

/**
 * Класс,  представляющий HTTP-запрос.
 */
@Value
@Builder
public class Request {
    /**
     * Описание запроса.
     */
    String description;
    /**
     * HTTP-метод запроса.
     */
    MethodType method;
    /**
     * Путь запроса.
     */
    Path path;
    /**
     * Заголовки запроса.
     */
    @Singular
    Map<String, String> headers;
    /**
     * Тело запроса.
     */
    String body;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Request)) {
            return false;
        }

        final Request request = (Request) o;

        return this.getMethod().equals(request.getMethod())
                && this.getPath().equals(request.getPath());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return Objects.hash(method, path);
    }
}
