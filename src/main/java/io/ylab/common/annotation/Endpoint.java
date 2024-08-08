package io.ylab.common.annotation;

import io.ylab.common.authorization.UserRole;
import io.ylab.common.method.MethodType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для обозначения метода контроллера как endpoint REST API.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Endpoint {
    /**
     * HTTP-метод endpoint-а (GET, POST, PUT, DELETE, etc.).
     *
     * @return HTTP-метод endpoint-а.
     */
    MethodType method();
    /**
     * Путь endpoint-а относительно пути,  указанного в аннотации {@link RequestMapping}.
     *
     * @return Путь endpoint-а.
     */
    String path() default "";
    /**
     * Флаг,  указывающий,  является ли  путь endpoint-а шаблоном.
     * Шаблонные пути могут содержать переменные,  заключенные в фигурные скобки (например,  "/users/{userId}").
     *
     * @return true,  если путь является шаблоном,  иначе false.
     */
    boolean isTemplate() default false;
    /**
     * Список ролей,  которым разрешен доступ к endpoint-у.
     *
     * @return Массив ролей.
     */
    UserRole[] roles() default {};
}
