package io.ylab.common.api;

import io.ylab.common.method.MethodType;
import lombok.Value;

import java.util.List;

/**
 * Класс, представляющий описание класса API.
 */
@Value
public class ApiClass {
    String path;
    MethodType method; // GET, POST, etc.
    List<ApiParameter> parameters;
}
