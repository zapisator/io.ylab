package io.ylab.common.header;

import lombok.Getter;

/**
 * Перечисление,  определяющее стандартные типы HTTP-заголовков.
 */
public enum HeaderType {
    AUTHORIZATION("Authorization:"),

    CONTENT_TYPE("Content-Type:"),

    LOCATION("Location:"),

    SERVER("Server:"),

    DATE("Date:"),

    CONTENT_LENGTH("Content-Length:"),

    USER_AGENT("Agent:");

    /**
     * Значение заголовка.
     */
    @Getter
    final String value;

    /**
     * Конструктор.
     *
     * @param value Значение заголовка.
     */
    HeaderType(String value) {
        this.value = value;
    }
}
