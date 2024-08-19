package io.ylab.frontend.presentation.view;

import io.ylab.frontend.infrastructure.resources.DefaultResourceProvider;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Представление для отображения полей ввода.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@ToString
public class DefaultFieldView {
    /**
     * Метка поля ввода.
     */
    private final String title;
    /**
     * Подсказка для поля ввода.
     */
    private final List<String> prompts;

    /**
     * Создает новый  экземпляр  {@link DefaultFieldView} на основе ключа для загрузки ресурсов.
     *
     * @param property Ключ для загрузки ресурсов.
     * @return Новый  экземпляр  {@link DefaultFieldView}.
     */
    public static DefaultFieldView create(String property) {
        final DefaultResourceProvider resource = new DefaultResourceProvider(property);
        final String title = resource.getString("title");
        final List<String> prompts = new ArrayList<>();
        boolean isContinue = true;

        for (int i = 1; i < 10 && isContinue; i++) {
            try {
                final String prompt = resource.getString("prompt." + i);

                prompts.add(prompt);
            } catch (Exception e) {
                isContinue = false;
            }
        }
        return new DefaultFieldView(title, prompts);
    }
}
