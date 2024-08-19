package io.ylab.common.api;

import lombok.Value;

import java.util.List;

/**
 * Класс, представляющий описание запроса API.
 */
@Value
public class ApiRequest {
    String path;
    String method; // GET, POST, etc.
    List<ApiParameter> parameters;
}
