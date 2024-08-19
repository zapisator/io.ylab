package io.ylab.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания базового пути для контроллера REST API.
 */
@Target(ElementType.TYPE) // Аннотация для класса
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestMapping {
    /**
     * Базовый путь для контроллера.
     *
     * @return Базовый путь.
     */
    String value(); // Общий префикс пути
}
