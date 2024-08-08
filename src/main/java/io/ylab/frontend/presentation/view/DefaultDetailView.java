package io.ylab.frontend.presentation.view;

import io.ylab.frontend.infrastructure.resources.DefaultResourceProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Представление для отображения деталей объекта.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DefaultDetailView {
    /**
     * Заголовок представления.
     */
    private final String title;
    /**
     * Строковое представление объекта.
     */
    private final String item;
    /**
     * Подсказка для пользователя.
     */
    private final String prompt;

    /**
     * Создает новый  экземпляр  {@link DefaultDetailView} на основе объекта.
     *
     * @param item     Объект для отображения.
     * @param property Ключ для загрузки ресурсов.
     * @param <T>      Тип объекта.
     * @return Новый  экземпляр  {@link DefaultDetailView}.
     */
    public static<T> DefaultDetailView create(T item, String property){
        final DefaultResourceProvider resource = new DefaultResourceProvider(property);
        final String title = resource.getString("title");
        final String prompt = resource.getString("prompt");
        return new DefaultDetailView(title, item.toString(), prompt);
    }
}
