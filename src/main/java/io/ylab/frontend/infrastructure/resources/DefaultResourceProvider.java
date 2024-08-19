package io.ylab.frontend.infrastructure.resources;

import java.util.ResourceBundle;

/**
 * Реализация по умолчанию для  {@link ResourceProvider},  которая использует  {@link ResourceBundle}
 * для загрузки текстовых ресурсов из  файлов свойств.
 */
public class DefaultResourceProvider implements ResourceProvider {
    /**
     * {@link ResourceBundle} для загрузки ресурсов.
     */
    private final ResourceBundle resourceBundle;

    /**
     * Конструктор.
     *
     * @param baseName Базовое имя файла свойств.
     */
    public DefaultResourceProvider(String baseName) {
        this.resourceBundle = ResourceBundle.getBundle(baseName);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(String key) {
        return resourceBundle.getString(key);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getString(String key, Object... args) {
        return String.format(resourceBundle.getString(key), args);
    }
}