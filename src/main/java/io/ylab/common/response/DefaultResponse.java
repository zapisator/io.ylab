package io.ylab.common.response;

import lombok.Builder;
import lombok.Singular;
import lombok.Value;

import java.util.List;

/**
 * Реализация HTTP-ответа по умолчанию.
 *
 * @param <T> Тип данных в ответе.
 */
@Value
@Builder
public class DefaultResponse<T> implements Response<T> {
    /**
     * Сообщение ответа.
     */
    String message;
    /**
     * Данные ответа.
     */
    T data;
    /**
     * Каноническое имя класса данных ответа.
     */
    String dataCanonicalClassName;
    /**
     * Сообщение об ошибке (если есть).
     */
    String errorMessage;
    /**
     * Список ссылок,  связанных с ответом.
     */
    @Singular
    List<Link> links;
}

