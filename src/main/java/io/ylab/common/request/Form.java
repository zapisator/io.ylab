package io.ylab.common.request;

/**
 * Интерфейс,  представляющий форму для сбора данных от пользователя.
 */
public interface Form {
    /**
     * Возвращает имя поля формы по его индексу.
     *
     * @param index Индекс поля.
     * @return Имя поля.
     */
    String getFieldName(int index);
    /**
     * Возвращает описание поля формы по его индексу.
     *
     * @param index Индекс поля.
     * @return Описание поля.
     */
    String getFieldDescription(int index);
    /**
     * Возвращает количество полей в форме.
     *
     * @return Количество полей.
     */
    int getFieldsCount();
}
