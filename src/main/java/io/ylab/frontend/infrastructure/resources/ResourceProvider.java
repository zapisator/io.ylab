package io.ylab.frontend.infrastructure.resources;

/**
 * Интерфейс для доступа к текстовым ресурсам приложения.
 */
public interface ResourceProvider {
    /**
     * Возвращает строковый ресурс по его ключу.
     *
     * @param key Ключ ресурса.
     * @return Строковый ресурс.
     */
    String getString(String key);
    /**
     * Возвращает строковый ресурс по его ключу,  подставляя в него  указанные  аргументы.
     *
     * @param key  Ключ ресурса.
     * @param args Аргументы для подстановки.
     * @return Строковый ресурс с  подставленными аргументами.
     */
    String getString(String key, Object... args);
}
