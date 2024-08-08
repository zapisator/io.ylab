package io.ylab.common.header;

/**
 * Интерфейс,  представляющий HTTP-заголовок.
 */
public interface Header {
    String getName();
    String getValue();
}
