package io.ylab.common.api;

import lombok.Value;

import java.lang.reflect.Type;

/**
 * Класс, представляющий описание параметра запроса API.
 */
@Value
public class ApiParameter {
    String key;
    Type type; // Тип параметра (String, int, etc.)
    String description;
}
