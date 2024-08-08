package io.ylab.backend.infrastructure.rest.controller;

import io.ylab.backend.domain.model.User;
import io.ylab.common.annotation.Endpoint;
import io.ylab.common.annotation.RequestMapping;
import io.ylab.common.authorization.UserRole;
import io.ylab.common.method.MethodType;
import io.ylab.common.request.Request;
import io.ylab.common.response.DefaultResponse;
import io.ylab.common.response.Response;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static io.ylab.common.authorization.UserRole.ADMIN;
import static io.ylab.common.authorization.UserRole.MANAGER;
import static io.ylab.common.method.MethodType.DELETE;
import static io.ylab.common.method.MethodType.GET;
import static io.ylab.common.method.MethodType.OPTIONS;
import static io.ylab.common.method.MethodType.POST;
import static io.ylab.common.method.MethodType.PUT;
import static java.util.Collections.singletonList;

/**
 * Контроллер,  отвечающий за обработку запросов,  связанных с пользователями.
 */
@RequestMapping("/users")
public class UserController implements CrudController<User> {
    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = OPTIONS, roles = {ADMIN, MANAGER})
    public Response<List<String>> menu() {
        return DefaultResponse.<List<String>>builder()
                .message("menu")
                .data(singletonList("menu"))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = POST, roles = {ADMIN})
    public Response<User> createOne(Request request) {
        return DefaultResponse.<User>builder()
                .message("createOne")
                .data(User.builder().build())
                .dataCanonicalClassName(User.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = GET, roles = {ADMIN, MANAGER})
    public Response<List<User>> readMany(Request request) {
        return DefaultResponse.<List<User>>builder()
                .message("seeAll")
                .data(singletonList(User.builder().build()))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = GET, path = "/{userId}", isTemplate = true, roles = {ADMIN, MANAGER})
    public Response<User> readOne(Request request) {
        return DefaultResponse.<User>builder()
                .message("seeOne")
                .data(User.builder().build())
                .dataCanonicalClassName(User.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = PUT, path = "/{userId}", roles = {ADMIN})
    public Response<User> updateOne(Request request) {
        return DefaultResponse.<User>builder()
                .message("editOne")
                .data(User.builder().build())
                .dataCanonicalClassName(User.class.getCanonicalName())
                .build();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Endpoint(method = DELETE, path = "/{userId}", isTemplate = true, roles = {ADMIN})
    public Response<User> deleteOne(Request request) {
        return DefaultResponse.<User>builder()
                .message("deleteOne")
                .data(User.builder().build())
                .dataCanonicalClassName(User.class.getCanonicalName())
                .build();
    }

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        UserController userController = new UserController();

        final List<Request> requests = Arrays.asList(
                Request.builder()
                        .method(MethodType.OPTIONS)
                        .path(Paths.get("/users"))
                        .build(),
                Request.builder()
                        .method(MethodType.GET)
                        .path(Paths.get("/users"))
                        .build(),
                Request.builder()
                        .method(MethodType.POST)
                        .path(Paths.get("/users"))
                        .build(),
                Request.builder()
                        .method(MethodType.PUT)
                        .path(Paths.get("/users/1"))
                        .build(),
                Request.builder()
                        .method(MethodType.DELETE)
                        .path(Paths.get("/users/1"))
                        .build()
        );
        for (Request request : requests) {
            Response response = processRequest(userController, request, Collections.singleton(ADMIN));
            System.out.println(request + "\n->\n" + response + "\n");
        }
    }

    private static Response processRequest(UserController userController, Request request, Set<UserRole> userRoles) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException {
        for (Method method : userController.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Endpoint.class)) {
                final Endpoint endpoint = method.getAnnotation(Endpoint.class);

                if (request.getMethod().equals(endpoint.method()) && hasAccess(endpoint.roles(), userRoles)) {
                    // Создаем шаблон регулярного выражения из пути аннотации
                    String regexPath = endpoint.path().replaceAll("\\{[^}]+\\}", "(\\\\d+)");

                    // Проверяем,  соответствует ли путь запроса шаблону
                    if (request.getPath().toString().matches(regexPath)) {
                        Response response = (Response) method.invoke(userController);
                        return response;
                    }
                }
            }
        }
        return null;
    }

    private static boolean hasAccess(UserRole[] allowedRoles, Set<UserRole> userRoles) {
        return userRoles.stream().anyMatch(role -> Arrays.asList(allowedRoles).contains(role));
    }
}
