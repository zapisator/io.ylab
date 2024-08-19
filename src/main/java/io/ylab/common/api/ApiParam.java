package io.ylab.common.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiParam {
    String name(); //  Имя  параметра
    String description(); //  Описание  параметра
    Class<?> type() default Object.class; //  Тип  параметра
    boolean required() default true; //  Является  ли  параметр  обязательным
}