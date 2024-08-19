package io.ylab.frontend.presentation.auth;


import io.ylab.auth.domain.model.User;
import io.ylab.auth.infrastructure.rest.AuthenticationController;
import io.ylab.common.request.Request;
import io.ylab.common.response.Response;
import lombok.RequiredArgsConstructor;

import static io.ylab.common.header.HeaderType.AUTHORIZATION;

/**
 * Реализация по умолчанию для  {@link AuthenticationManager}.
 */
@RequiredArgsConstructor
public class DefaultAuthenticationManager implements AuthenticationManager {
    /**
     * Контроллер аутентификации backend-а.
     */
    private final AuthenticationController controller;
    /**
     * Текущий аутентифицированный пользователь.
     */
    private User user;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean login(String username, String password) {
        final Response<User> response = controller.login(Request.builder()
                .header(
                        AUTHORIZATION.getValue(),
                        AUTHORIZATION.getValue() + " " + username + ":" + password
                )
                .build());
        if (response != null) {
            user = response.getData();
        }
        return user != null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void logout() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User getCurrentUser() {
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getToken() {
        return "";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void refreshToken() {

    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLoggedIn() {
        return false;
    }
}
