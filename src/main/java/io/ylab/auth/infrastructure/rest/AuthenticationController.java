package io.ylab.auth.infrastructure.rest;

import io.ylab.auth.domain.model.User;
import io.ylab.auth.domain.service.AuthenticationService;
import io.ylab.common.annotation.Endpoint;
import io.ylab.common.annotation.RequestMapping;
import io.ylab.common.request.DefaultForm;
import io.ylab.common.request.Form;
import io.ylab.common.request.Request;
import io.ylab.common.response.DefaultResponse;
import io.ylab.common.response.Response;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

import static io.ylab.common.header.HeaderType.AUTHORIZATION;
import static io.ylab.common.method.MethodType.GET;
import static io.ylab.common.method.MethodType.OPTIONS;
import static io.ylab.common.method.MethodType.POST;

@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    /**
     * Сервис аутентификации.
     */
    private final AuthenticationService service;

    /**
     * Обрабатывает OPTIONS-запрос для получения опций главного меню.
     *
     * @return Ответ с опциями главного меню.
     */
    @Endpoint(method = OPTIONS)
    public Response<List<String>> getMainMenuOptions() {
        return DefaultResponse.<List<String>>builder()
                .message("Main Menu Options")
                .data(Collections.singletonList("Main Menu Options"))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает OPTIONS-запрос для получения опций логина.
     *
     * @return Ответ с опциями логина.
     */
    @Endpoint(method = OPTIONS, path = "/logins")
    public Response<List<String>> getLoginOptions() {
        return DefaultResponse.<List<String>>builder()
                .message("Login Options")
                .data(Collections.singletonList("Login Options"))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает GET-запрос для получения формы логина.
     *
     * @param request Запрос.
     * @return Ответ с формой логина.
     */
    @Endpoint(method = GET, path = "/logins")
    public Response<Form> getLogin(Request request) {
        return DefaultResponse.<Form>builder()
                .message("Please enter your credentials:")
                .data(DefaultForm.builder()
                        .fieldsCount(2)
                        .fieldName("username")
                        .fieldDescription("Username:")
                        .fieldName("password")
                        .fieldDescription("Password:")
                        .build())
                .dataCanonicalClassName(Form.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает POST-запрос для аутентификации пользователя.
     *
     * @param request Запрос, содержащий имя пользователя и пароль.
     * @return Ответ с информацией о пользователе в случае успешной аутентификации, иначе null.
     */
    @Endpoint(method = POST, path = "/logins")
    public Response<User> login(Request request) {
        final String authHeader = request.getHeaders().get(AUTHORIZATION.getValue());

        if (authHeader != null) {
            final String[] credentials = authHeader.split(":");
            final String userName = credentials[1].trim();
            final String password = credentials[2].trim();

            if (service.authenticate(userName, password)) {
                return DefaultResponse.<User>builder()
                        .message("login")
                        .data(service.getCurrentUser())
                        .dataCanonicalClassName(User.class.getCanonicalName())
                        .build();
            }
        }
        return null;
    }

    /**
     * Обрабатывает OPTIONS-запрос для получения опций регистрации.
     *
     * @return Ответ с опциями регистрации.
     */
    @Endpoint(method = OPTIONS, path = "/registrations")
    public Response<List<String>> getRegistrationOptions() {
        return DefaultResponse.<List<String>>builder()
                .message("Registration Options")
                .data(Collections.singletonList("Registration Options"))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает GET-запрос для получения формы регистрации.
     *
     * @param request Запрос.
     * @return Ответ с формой регистрации.
     */
    @Endpoint(method = GET, path = "/registrations")
    public Response<Form> getRegistration(Request request) {
        return DefaultResponse.<Form>builder()
                .message("Please enter your registration.properties data:")
                .data(DefaultForm.builder()
                        .fieldsCount(3)
                        .fieldName("username")
                        .fieldDescription("Username:")
                        .fieldName("password")
                        .fieldDescription("Password:")
                        .fieldName("email")
                        .fieldDescription("Email:")
                        .build())
                .dataCanonicalClassName(Form.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает POST-запрос для регистрации пользователя.
     *
     * @param request Запрос.
     * @return Ответ с сообщением об успешной регистрации.
     */
    @Endpoint(method = POST, path = "/registrations")
    public Response<String> registration(Request request) {
        return DefaultResponse.<String>builder()
                .message("registration.properties")
                .data("registration.properties successful")
                .dataCanonicalClassName(String.class.getCanonicalName())
                .build();
    }
}
