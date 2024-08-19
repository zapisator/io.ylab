package io.ylab.common.response;

import io.ylab.common.header.Header;
import io.ylab.common.method.MethodType;
import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.nio.file.Path;
import java.util.List;

/**
 * Класс,  представляющий ссылку HATEOAS,  связанную с HTTP-ответом.
 */
@Value
@Builder
public class Link {
    /**
     * HTTP-метод для ссылки.
     */
    MethodType method; // HTTP-метод (GET, POST, PUT, DELETE)
    /**
     * Путь ссылки.
     */
    Path path; // URL-путь
    /**
     * Описание ссылки.
     */
    String description; // Описание действия
    /**
     * Список заголовков для ссылки.
     */
    @Singular
    List<Header> headers;
}
