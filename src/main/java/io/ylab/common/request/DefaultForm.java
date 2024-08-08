package io.ylab.common.request;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.Singular;

import java.util.List;

/**
 * Реализация формы,  используемая для  сбора  данных  от  пользователя.
 */
@Builder
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class DefaultForm implements Form {
    /**
     * Количество полей в форме.
     */
    private final int fieldsCount;
    /**
     * Список имен полей формы.
     */
    @Singular("fieldName")
    private final List<String> fieldName;

    /**
     * Список описаний полей формы.
     */
    @Singular("fieldDescription")
    private final List<String> fieldDescription;

    /**
     * {@inheritDoc}
     *
     * @throws RuntimeException Если  индекс  некорректен.
     */
    @Override
    public String getFieldName(int index) {
        if (isIncorrectIndex(index)){
            throw new RuntimeException();
        }
        return fieldName.get(index);
    }

    /**
     * {@inheritDoc}
     *
     * @throws RuntimeException Если  индекс  некорректен.
     */
    @Override
    public String getFieldDescription(int index) {
        if (isIncorrectIndex(index)){
            throw new RuntimeException();
        }
        return fieldDescription.get(index);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int getFieldsCount() {
        return fieldsCount;
    }

    /**
     * Проверяет,  является ли  индекс  некорректным.
     *
     * @param index Индекс  для  проверки.
     * @return true,  если  индекс  некорректен,  иначе  false.
     */
    private boolean isIncorrectIndex(int index) {
        return 0 > index || index > fieldsCount - 1;
    }
}
