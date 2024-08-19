package io.ylab.frontend.presentation.auth;

import io.ylab.auth.domain.model.User;
import io.ylab.auth.infrastructure.rest.AuthenticationController;
import io.ylab.common.authorization.UserRole;
import io.ylab.common.request.Request;
import io.ylab.common.response.DefaultResponse;
import io.ylab.common.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DefaultAuthenticationManagerTest {

    @Mock
    private AuthenticationController controller;

    @InjectMocks
    private DefaultAuthenticationManager manager;

    @Test
    void login_shouldReturnTrueForSuccessfulLogin() {
        // Подготавливаем мок-объект
        User user = new User("testUser", "passwordHash", singletonList(UserRole.CLIENT));
        Response<User> response = DefaultResponse.<User>builder().data(user).build();
        when(controller.login(any())).thenReturn(response);

        // Вызываем тестируемый метод
        boolean result = manager.login("testUser", "testPassword");

        // Проверяем результат
        assertThat(result).isTrue();
        assertThat(manager.getCurrentUser()).isEqualTo(user);
    }

    @Test
    void login_shouldReturnFalseForFailedLogin() {
        // Подготавливаем мок-объект
        when(controller.login(any())).thenReturn(null);

        // Вызываем тестируемый метод
        boolean result = manager.login("testUser", "wrongPassword");

        // Проверяем результат
        assertThat(result).isFalse();
        assertThat(manager.getCurrentUser()).isNull();
    }

    @Test
    void login_shouldSetCurrentUserAfterSuccessfulLogin() {
        // Подготавливаем мок-объект
        User expectedUser = new User("testUser", "passwordHash", singletonList(UserRole.CLIENT));
        Response<User> response = DefaultResponse.<User>builder().data(expectedUser).build();
        when(controller.login(any(Request.class))).thenReturn(response);

        // Вызываем тестируемый метод
        boolean result = manager.login("testUser", "testPassword");

        // Проверяем результат
        assertThat(result).isTrue();
        assertThat(manager.getCurrentUser()).isEqualTo(expectedUser);
    }
}