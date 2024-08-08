package io.ylab.backend.infrastructure.rest.router;

import io.ylab.common.authorization.UserRole;
import io.ylab.common.method.MethodType;

/**
 * Интерфейс,  представляющий определение endpoint-а.
 */
public interface EndpointDefinition {
    /**
     * Возвращает HTTP-метод endpoint-а.
     *
     * @return HTTP-метод.
     */
    MethodType getMethod();
    /**
     * Возвращает путь endpoint-а.
     *
     * @return Путь.
     */
    String getPath();
    /**
     * Возвращает список ролей,  которым разрешен доступ к endpoint-у.
     *
     * @return Список ролей.
     */
    UserRole[] getRoles();
    /**
     * Возвращает флаг,  указывающий,  является ли  путь endpoint-а шаблоном.
     *
     * @return true,  если путь является шаблоном,  иначе false.
     */
    boolean isTemplate();
}
