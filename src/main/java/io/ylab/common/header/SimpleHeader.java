package io.ylab.common.header;

import lombok.Value;

/**
 * Простая реализация  {@link Header},  содержащая имя и  значение заголовка.
 */
@Value
public class SimpleHeader implements Header  {
    /**
     * Имя заголовка.
     */
    String name;
    /**
     * Значение заголовка.
     */
    String value;

    /**
     * Конструктор.
     *
     * @param name  Имя заголовка.
     * @param value Значение заголовка.
     */
    public SimpleHeader(String name, String value){
        this.name = name;
        this.value = value;
    }

    /**
     * Конструктор,  создающий заголовок на основе типа заголовка и  типа значения.
     *
     * @param type  Тип заголовка.
     * @param valueType Тип значения.
     */
    public SimpleHeader(HeaderType type, HeaderValueType valueType){
        this(type.toString(), valueType.toString());
    }
}
