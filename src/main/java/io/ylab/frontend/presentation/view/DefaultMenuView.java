package io.ylab.frontend.presentation.view;

import io.ylab.frontend.infrastructure.resources.DefaultResourceProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Представление для отображения меню.
 */
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class DefaultMenuView {
    /**
     * Заголовок меню.
     */
    private final String title;
    /**
     * Список пунктов меню.
     */
    private final List<String> items;
    /**
     * Подсказка для пользователя.
     */
    private final String prompt;

    /**
     * Создает новый  экземпляр  {@link DefaultMenuView} на основе ключа для загрузки ресурсов.
     *
     * @param property Ключ для загрузки ресурсов.
     * @return Новый  экземпляр  {@link DefaultMenuView}.
     */
    public static DefaultMenuView create(String property) {
        final DefaultResourceProvider resource = new DefaultResourceProvider(property);
        final String title = resource.getString("title");
        final List<String> items = items(resource);
        final String prompt = prompt(resource, items);
        return new DefaultMenuView(title, items, prompt);
    }

    /**
     * Формирует подсказку для пользователя на основе количества пунктов меню.
     *
     * @param resource Поставщик ресурсов.
     * @param items    Список пунктов меню.
     * @return Подсказка для пользователя.
     */
    private static String prompt(DefaultResourceProvider resource, List<String> items) {
        final String promptStart = resource.getString("prompt");
        return promptStart + " (0 - " + (items.size() - 1) + "): ";
    }

    /**
     * Загружает пункты меню из  ресурсов.
     *
     * @param resource Поставщик ресурсов.
     * @return Список пунктов меню.
     */
    private static List<String> items(DefaultResourceProvider resource) {
        final List<String> items = new ArrayList<>();
        boolean isContinue = true;

        for (int i = 1; i < 10 && isContinue; i++) {
            try {
                final String prompt = resource.getString("item." + i);

                items.add(prompt);
            } catch (Exception e) {
                isContinue = false;
            }
        }
        return items;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final String itemsToString = IntStream.range(0, items.size())
                .mapToObj(i ->i + ". " + items.get(i))
                .collect(Collectors.joining("\n"));
        return title + '\n' +
                itemsToString + '\n' + prompt + '\n';
    }

    public static void main(String[] args) {
        final DefaultMenuView view = DefaultMenuView.create("root");

        System.out.println(view);
//        System.out.println(view.title);
//        System.out.println(view.items);
//        for (int i = 0; i < view.items.size(); i++) {
//            System.out.println(i + ". " + view.items.get(i));
//        }
//        System.out.println(view.prompt);
    }

}
