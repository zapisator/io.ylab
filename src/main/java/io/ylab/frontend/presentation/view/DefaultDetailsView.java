package io.ylab.frontend.presentation.view;

import io.ylab.frontend.infrastructure.resources.DefaultResourceProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Представление для отображения списка деталей объектов.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
public class DefaultDetailsView {
    /**
     * Заголовок представления.
     */
    private final String title;
    /**
     * Список элементов представления.
     */
    private final List<String> items;
    /**
     * Подсказка для пользователя.
     */
    private final String prompt;

    /**
     * Создает новый  экземпляр  {@link DefaultDetailsView} на основе списка объектов.
     *
     * @param items     Список объектов.
     * @param property Ключ для загрузки ресурсов.
     * @param <T>      Тип объектов в списке.
     * @return Новый  экземпляр  {@link DefaultDetailsView}.
     */
    public static <T> DefaultDetailsView create(List<T> items, String property) {
        final DefaultResourceProvider resource = new DefaultResourceProvider(property);
        final String title = resource.getString("title");
        final String prompt = resource.getString("prompt");
        return new DefaultDetailsView(
                title,
                items.stream()
                        .map(Object::toString)
                        .collect(Collectors.toList()),
                prompt
        );
    }
}
