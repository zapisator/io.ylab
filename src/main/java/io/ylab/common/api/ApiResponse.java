package io.ylab.common.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiResponse {
    int code(); //  Код  статуса  HTTP
    String message(); //  Описание  ответа
    Class<?> type() default Object.class; //  Тип  возвращаемых  данных
}
