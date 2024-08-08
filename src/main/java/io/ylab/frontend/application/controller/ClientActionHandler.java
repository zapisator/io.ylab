package io.ylab.frontend.application.controller;

import io.ylab.backend.domain.model.Car;
import io.ylab.backend.infrastructure.rest.router.RestRouter;
import io.ylab.common.header.HeaderType;
import io.ylab.common.method.MethodType;
import io.ylab.common.request.Request;
import io.ylab.common.response.Response;
import io.ylab.frontend.infrastructure.console.ConsoleHelper;
import io.ylab.frontend.presentation.view.DefaultMenuView;

import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static io.ylab.common.authorization.UserRole.CLIENT;
import static io.ylab.common.method.MethodType.GET;

/**
 * Обработчик действий для роли клиента.
 * Отображает меню клиента и  обрабатывает выбор пользователя.
 */
public class ClientActionHandler extends DefaultMenuActionHandler {
    /**
     * Помощник для работы с консолью.
     */
    private final ConsoleHelper console;
    /**
     * Роутер REST API.
     */
    private final RestRouter router;

    /**
     * Конструктор.
     *
     * @param view    Представление меню.
     * @param console Помощник для работы с консолью.
     * @param router  Роутер REST API.
     */
    public ClientActionHandler(DefaultMenuView view, ConsoleHelper console, RestRouter router) {
        super(view);
        this.console = console;
        this.router = router;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Response<?> handleAndGetState() {
        console.writeLine(view.toString());
        console.writeLine("3. Exit");
        final String option = console.readLine();
        System.out.println("option: " + option);

        switch (option) {
            case "0":
                handleBrowseCars();
                break;
            case "1":
                handleMyOrders();
                break;
            case "2":
                handleMyCart();
                break;
            case "3":
                handleLogout();
                break;
            default:
                console.writeLine("Invalid option selected.");
        }

        return null; // Возвращаем null, так как не меняем состояние сессии
    }

    // Заглушки для обработки действий
    private void handleBrowseCars() {
        Response<List<Car>> response = router.handleRequest(Request.builder()
                .header(
                        HeaderType.AUTHORIZATION.getValue(),
                        HeaderType.AUTHORIZATION.getValue() + " " + "client:" + CLIENT)
                        .method(GET)
                        .path(Paths.get("/cars"))
                .build()
        );
        console.writeLine(IntStream.range(0, response.getData().size())
                .mapToObj(i -> i + ". " + response.getData().get(i))
                .collect(Collectors.joining("\n"))
        );
    }

    private void handleMyOrders() {
        console.writeLine("My Orders functionality not yet implemented.");
    }

    private void handleMyCart() {
        console.writeLine("My Cart functionality not yet implemented.");
    }

    private void handleLogout() {
        console.writeLine("Logging out...");
        System.exit(0);
        // TODO: Реализовать логику выхода (обнулить session в ActionRouter)
    }
}
