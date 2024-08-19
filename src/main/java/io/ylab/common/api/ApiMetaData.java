package io.ylab.common.api;

import lombok.Value;

import java.util.List;

/**
 * Класс, представляющий описание API.
 */
@Value
public class ApiMetaData    {
    List<ApiClass> classes;
}
