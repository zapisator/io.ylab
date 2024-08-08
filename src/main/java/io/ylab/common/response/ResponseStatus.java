package io.ylab.common.response;

/**
 * Перечисление,  определяющее коды и  сообщения статусов HTTP-ответов.
 */
public enum ResponseStatus {
    OK(200, "OK"),
    CREATED(201, "Created"),
    NO_CONTENT(204, "No Content"),
    MOVED_PERMANENTLY(301, "Moved Permanently"),
    FOUND(302, "Found"),
    SEE_OTHER(303, "See Other"),
    NOT_MODIFIED(304, "Not Modified"),
    TEMPORARY_REDIRECT(307, "Temporary Redirect"),
    PERMANENT_REDIRECT(308, "Permanent Redirect"),
    BAD_REQUEST(400, "Bad Request"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    NOT_IMPLEMENTED(501, "Not Implemented"),
    BAD_GATEBAY(502, "Bad Gateway"),
    SERICE_UNAVAILABLE(503, "Service Unavailable"),
    GATWAY_TIMEOUT(504, "Gateway Timeout"),
    HTTP_VERSION_NOT_SUPPORTED(505, "HTTP Version Not Supported");

    /**
     * Код статуса.
     */
    private final int code;
    /**
     * Сообщение статуса.
     */
    private final String name;

    /**
     * Конструктор.
     *
     * @param code Код статуса.
     * @param name Сообщение статуса.
     */
    ResponseStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }
}
