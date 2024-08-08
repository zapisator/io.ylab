package io.ylab.frontend.application.controller;

import io.ylab.auth.domain.model.User;
import io.ylab.auth.domain.service.AuthenticationService;
import io.ylab.auth.domain.service.FileAuthenticationService;
import io.ylab.auth.infrastructure.rest.AuthenticationController;
import io.ylab.backend.domain.service.CarService;
import io.ylab.backend.infrastructure.repository.InMemoryCarRepository;
import io.ylab.backend.infrastructure.rest.controller.CarController;
import io.ylab.backend.infrastructure.rest.controller.LogController;
import io.ylab.backend.infrastructure.rest.controller.OrderController;
import io.ylab.backend.infrastructure.rest.controller.RootController;
import io.ylab.backend.infrastructure.rest.controller.UserController;
import io.ylab.backend.infrastructure.rest.router.MapBasedRestRouter;
import io.ylab.backend.infrastructure.rest.router.RestRouter;
import io.ylab.common.authorization.UserRole;
import io.ylab.common.response.Response;
import io.ylab.frontend.domain.model.Session;
import io.ylab.frontend.infrastructure.console.ConsoleHelper;
import io.ylab.frontend.presentation.auth.AuthenticationManager;
import io.ylab.frontend.presentation.auth.DefaultAuthenticationManager;
import io.ylab.frontend.presentation.view.DefaultMenuView;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static io.ylab.common.authorization.UserRole.ADMIN;
import static io.ylab.common.authorization.UserRole.CLIENT;
import static io.ylab.common.authorization.UserRole.MANAGER;

/**
 * Класс,  отвечающий за маршрутизацию действий пользователя во frontend-е.
 * Содержит карту обработчиков действий для каждой роли пользователя.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ActionRouter {
    /**
     * Помощник для работы с консолью.
     */
    final ConsoleHelper console = new ConsoleHelper();
    /**
     * Карта обработчиков действий,  где ключом является имя роли,  а значением -  обработчик.
     */
    final Map<String, ActionHandler> handlers = new HashMap<>();
    /**
     * Текущая сессия пользователя.
     */
    private Session session;

    /**
     * Создает новый  экземпляр  {@link ActionRouter},  инициализирует обработчики действий
     * для каждой роли и  устанавливает начальное состояние сессии.
     *
     * @return Новый  экземпляр  {@link ActionRouter}.
     */
    public static ActionRouter create() {
        final ActionRouter router = new ActionRouter();
        final AuthenticationService authenticationService = FileAuthenticationService.create();
        final AuthenticationController controller = new AuthenticationController(authenticationService);
        final AuthenticationManager authenticationManager = new DefaultAuthenticationManager(controller);
        final RestRouter restRouter = MapBasedRestRouter.with(
                new CarController(new CarService(new InMemoryCarRepository())),
                new LogController(),
                new OrderController(),
                new RootController(),
                new UserController()
        );

        router.handlers.put("root", new LoginActionHandler(
                DefaultMenuView.create("root"),
                router.console,
                authenticationManager
        ));
        //  Регистрация  обработчиков  для  ролей
        router.handlers.put("admin", new AdminActionHandler(
                DefaultMenuView.create("admin"),
                router.console
        ));
        router.handlers.put("manager", new ManagerActionHandler(
                DefaultMenuView.create("manager"),
                router.console
        ));
        router.handlers.put("client", new ClientActionHandler(
                DefaultMenuView.create("client"),
                router.console, restRouter
        ));

        // TODO: убрать это присвоение
        router.session = Session.builder()
                .role(CLIENT)
                .user(new User("client", "client", Collections.singletonList(CLIENT)))
                .build();
        return router;
    }

    /**
     * Запускает цикл обработки действий пользователя.
     * Определяет текущую роль пользователя и  вызывает соответствующий обработчик.
     */
    public void run() {
        boolean isRunning = true;

        while (isRunning) {
            if (session == null) {
                final ActionHandler handler = handlers.get("root");

                session = ((Response<Session>) handler.handleAndGetState()).getData();
            } else {
                final UserRole role = session.getRole();

                if (role == ADMIN) {
                    handlers.get("admin").handleAndGetState();
                } else if (role == MANAGER) {
                    handlers.get("manager").handleAndGetState();
                } else if (role == CLIENT) {
                    handlers.get("client").handleAndGetState();
                }
            }
        }
    }

    public static void main(String[] args) {
        final ActionRouter actionRouter = ActionRouter.create();
        actionRouter.run();
    }
}
