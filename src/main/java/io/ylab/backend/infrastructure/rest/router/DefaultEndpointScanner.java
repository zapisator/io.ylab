package io.ylab.backend.infrastructure.rest.router;

import io.ylab.backend.domain.service.CarService;
import io.ylab.backend.infrastructure.repository.InMemoryCarRepository;
import io.ylab.backend.infrastructure.rest.controller.CarController;
import io.ylab.backend.infrastructure.rest.controller.CrudController;
import io.ylab.backend.infrastructure.rest.controller.LogController;
import io.ylab.backend.infrastructure.rest.controller.OrderController;
import io.ylab.backend.infrastructure.rest.controller.RootController;
import io.ylab.backend.infrastructure.rest.controller.UserController;
import io.ylab.common.annotation.Endpoint;
import io.ylab.common.annotation.RequestMapping;
import io.ylab.common.authorization.UserRole;
import io.ylab.common.method.MethodType;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * Реализация сканера endpoint-ов,  которая сканирует классы контроллеров
 * на наличие аннотаций {@link Endpoint} и  регистрирует найденные endpoint-ы в роутере.
 */
@RequiredArgsConstructor
public class DefaultEndpointScanner implements EndpointScanner {
    /**
     * Роутер,  в который будут регистрироваться найденные endpoint-ы.
     */
    private final RestRouter router;

    /**
     * {@inheritDoc}
     */
    @Override
    public void scan(CrudController<?>... controllerClasses) {
        Arrays.stream(controllerClasses).forEach(crudController -> {
            final Class<?> crudControllerClass = crudController.getClass();
            final RequestMapping mapping = crudControllerClass.getAnnotation(RequestMapping.class);

            for (Method method : crudControllerClass.getDeclaredMethods()) {
                if (method.isAnnotationPresent(Endpoint.class)) {
                    final EndpointDefinition definition = getEndpointDefinition(method, mapping);
                    
                    router.registerEndpoint(
                            definition,
                            new SimpleEndpointInformation(crudController, method)
                    );
                }
            }
        });
    }

    /**
     * Создает определение endpoint-а на основе аннотации {@link Endpoint} и  {@link RequestMapping}.
     *
     * @param method Метод контроллера,  аннотированный {@link Endpoint}.
     * @param mapping Аннотация {@link RequestMapping} для класса контроллера.
     * @return Определение endpoint-а.
     */
    private SimpleEndpointDefinition getEndpointDefinition(Method method, RequestMapping mapping) {
        final Endpoint endpoint = method.getAnnotation(Endpoint.class);
        final String path = mapping.value() + endpoint.path();
        final MethodType httpMethod = endpoint.method();
        final UserRole[] roles = endpoint.roles();
        final boolean isTemplate = endpoint.isTemplate();
        return new SimpleEndpointDefinition(
                httpMethod, path, roles, isTemplate
        );
    }

    public static void main(String[] args) {
        MapBasedRestRouter.with(new CarController(new CarService(new InMemoryCarRepository())),
                new LogController(),
                new OrderController(),
                new RootController(),
                new UserController()
        );
    }
}