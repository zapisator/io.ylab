package io.ylab.frontend.infrastructure.resources;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.MissingResourceException;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class DefaultResourceProviderTest {
    private DefaultResourceProvider provider;

    @BeforeEach
    void setUp() {
        // Укажите имя вашего файла ресурсов без расширения ".properties"
        provider = new DefaultResourceProvider("login");
    }

    @Test
    void getString_shouldReturnCorrectStringForExistingKey() {
        String result = provider.getString("prompt.1"); // Замените "testKey" на реальный ключ из вашего файла ресурсов
        assertThat(result).isEqualTo("Enter username:"); // Замените "Test Value" на ожидаемое значение
    }

    @Test
    void getString_shouldThrowMissingResourceExceptionForNonExistingKey() {
        assertThatThrownBy(() -> provider.getString("nonExistingKey"))
                .isInstanceOf(MissingResourceException.class);
    }

    @Test
    void getString_shouldReturnStringWithPlaceholderForExistingKeyWithArgs() {
        String result = provider.getString("login.form.alert.success", "TestUser");
        assertThat(result).isEqualTo("Welcome, {0}! You are logged in.");
    }

}