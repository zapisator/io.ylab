package io.ylab.backend.infrastructure.rest.router;

import io.ylab.backend.domain.service.AuthService;
import io.ylab.backend.domain.service.CarService;
import io.ylab.backend.infrastructure.repository.InMemoryCarRepository;
import io.ylab.backend.infrastructure.rest.controller.CarController;
import io.ylab.backend.infrastructure.rest.controller.CrudController;
import io.ylab.backend.infrastructure.rest.controller.LogController;
import io.ylab.backend.infrastructure.rest.controller.OrderController;
import io.ylab.backend.infrastructure.rest.controller.RootController;
import io.ylab.backend.infrastructure.rest.controller.UserController;
import io.ylab.common.authorization.UserRole;
import io.ylab.common.header.HeaderType;
import io.ylab.common.method.MethodType;
import io.ylab.common.request.Request;
import io.ylab.common.response.Response;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import static io.ylab.common.header.HeaderType.AUTHORIZATION;
import static io.ylab.common.method.MethodType.GET;

/**
 * Реализация роутера REST API,  которая использует  {@link Map}
 * для сопоставления запросов с  соответствующими endpoint-ами.
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class MapBasedRestRouter implements RestRouter {
    /**
     * {@link Map},  содержащая сопоставления определений endpoint-ов с  их  информацией.
     */
    private final Map<EndpointDefinition, EndpointInformation> endpoints = new HashMap<>();
    /**
     * {@inheritDoc}
     */
    private final AuthService authService = null;

    /**
     * {@inheritDoc}
     */
    @Override
    public void registerEndpoint(EndpointDefinition definition, EndpointInformation information) {
//        System.out.println("definition: " + definition + "\ninformation: " + information + "\n");
        endpoints.put(definition, information);
    }

    /**
     * Создает новый  экземпляр  {@link MapBasedRestRouter} и  сканирует  указанные  контроллеры
     * на  наличие  endpoint-ов  с  помощью  {@link DefaultEndpointScanner}.
     *
     * @param crudControllers Контроллеры  для  сканирования.
     * @return Новый  экземпляр  {@link MapBasedRestRouter}.
     */
    public static MapBasedRestRouter with(CrudController<?>... crudControllers) {
        final MapBasedRestRouter router = new MapBasedRestRouter();
        new DefaultEndpointScanner(router).scan(crudControllers);
        return router;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<?> handleRequest(Request request) {
        final MethodType methodType = request.getMethod();
        final String path = request.getPath().toString();
        final UserRole[] roles = extractRolesFromToken(request.getHeaders().get(AUTHORIZATION.getValue()));
        final EndpointDefinition definition = new SimpleEndpointDefinition(methodType, path, roles, true);
        final EndpointInformation information = endpoints.get(definition);
        if (information == null || information.getController() == null) {
            System.out.println("501 Not Implemented");
            return null;
        }
        final CrudController<?> controller = information.getController();
        final Method handlerMethod = information.getMethod();

//        authService.authorize(request, definition.getRoles());


        try {
            return (Response) handlerMethod.invoke(controller, request);
        } catch (IllegalAccessException | InvocationTargetException | SecurityException e) {
            throw new RuntimeException();
        }
    }

    /**
     * Извлекает список ролей из токена авторизации.
     *
     * @param token Токен авторизации.
     * @return Список ролей.
     */
    private UserRole[] extractRolesFromToken(String token) {
        final String[] roleNames = token.split(":")[2].split(",");
        final UserRole[] roles = new UserRole[roleNames.length];

        for (int i = 0; i < roles.length; i++) {
            roles[i] = UserRole.valueOf(roleNames[i]);
        }
        return roles;
    }

    public static void main(String[] args) {
        // ... (другой код) ...
        final List<Request> requests = Arrays.asList(
                createRequestWithToken(GET, "/users", "user1:GUEST"),
                createRequestWithToken(GET, "/users", "user1:ADMIN"),
                createRequestWithToken(GET, "/users", "user1:MANAGER"),
                createRequestWithToken(GET, "/users", "user1:CLIENT"),
                createRequestWithToken(GET, "/cars", "user1:GUEST"),
                createRequestWithToken(GET, "/cars", "user1:ADMIN"),
                createRequestWithToken(GET, "/cars", "user1:MANAGER"),
                createRequestWithToken(GET, "/cars", "user1:CLIENT"),
                createRequestWithToken(GET, "/orders", "user1:GUEST"),
                createRequestWithToken(GET, "/orders", "user1:ADMIN"),
                createRequestWithToken(GET, "/orders", "user1:MANAGER"),
                createRequestWithToken(GET, "/orders", "user1:CLIENT"),
                createRequestWithToken(GET, "/", "user1:GUEST"),
                createRequestWithToken(GET, "/", "user1:ADMIN"),
                createRequestWithToken(GET, "/", "user1:MANAGER"),
                createRequestWithToken(GET, "/", "user1:CLIENT"),
                createRequestWithToken(GET, "/logs", "user1:GUEST"),
                createRequestWithToken(GET, "/logs", "user1:ADMIN"),
                createRequestWithToken(GET, "/logs", "user1:MANAGER"),
                createRequestWithToken(GET, "/logs", "user1:CLIENT")
        );

        // Создание роутера (AuthService пока не нужен)
        MapBasedRestRouter router = new MapBasedRestRouter();
        new DefaultEndpointScanner(router)
                .scan(
                        new CarController(new CarService(new InMemoryCarRepository())),
                        new LogController(),
                        new OrderController(),
                        new RootController(),
                        new UserController()
                );
        for (Request request : requests) {
            System.out.println(router.handleRequest(request));
        }

        // ... (другой код) ...
    }

    // Вспомогательный метод для создания запроса с токеном
    private static Request createRequestWithToken(MethodType method, String path, String token) {
        Map<String, String> headers = new HashMap<>();
        headers.put(HeaderType.AUTHORIZATION.getValue(), token);
        return Request.builder()
                .method(method)
                .path(Paths.get(path))
                .headers(headers)
                .build();
    }

    private boolean matchPath(String requestPath, String endpointPath, boolean isTemplate) {
        if (isTemplate) {
            // Используем регулярные выражения для сопоставления с параметрами пути
            String regexPath = endpointPath.replaceAll("\\{[^}]+}", "(d+)");
            return Pattern.matches(regexPath, requestPath);
        } else {
            // Простое сравнение путей
            return requestPath.equals(endpointPath);
        }
    }
}
