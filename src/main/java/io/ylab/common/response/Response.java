package io.ylab.common.response;

import java.util.List;

/**
 * Интерфейс,  представляющий HTTP-ответ.
 *
 * @param <T> Тип данных в ответе.
 */
public interface Response<T> {
    /**
     * Возвращает сообщение ответа.
     *
     * @return Сообщение ответа.
     */
    String getMessage();
    /**
     * Возвращает данные ответа.
     *
     * @return Данные ответа.
     */
    T getData();
    /**
     * Возвращает каноническое имя класса данных ответа.
     *
     * @return Каноническое имя класса.
     */
    String getDataCanonicalClassName();
    /**
     * Возвращает сообщение об ошибке (если есть).
     *
     * @return Сообщение об ошибке.
     */
    String getErrorMessage();
    /**
     * Возвращает список ссылок,  связанных с ответом.
     *
     * @return Список ссылок.
     */
    List<Link> getLinks();
}