package io.ylab.frontend.application.controller;

import io.ylab.backend.domain.model.CarStatus;
import io.ylab.backend.infrastructure.rest.router.RestRouter;
import io.ylab.common.response.DefaultResponse;
import io.ylab.common.response.Response;
import io.ylab.frontend.domain.model.Car;
import io.ylab.frontend.infrastructure.console.ConsoleHelper;
import io.ylab.frontend.presentation.view.DefaultMenuView;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static java.util.Collections.singletonList;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClientActionHandlerTest {

    @Mock
    private DefaultMenuView view;

    @Mock
    private ConsoleHelper console;

    @Mock
    private RestRouter router;

    @InjectMocks
    private ClientActionHandler handler;

    @Test
    void handleAndGetState_shouldHandleBrowseCarsOption() {
        final List<Car> cars = singletonList(Car.builder()
                .id(1L)
                .brand("Test Brand")
                .model("Test Model")
                .year(2023)
                .price(20000.0)
                .status(CarStatus.AVAILABLE)
                .build());
        final Response<List<Car>> response = DefaultResponse.<List<Car>>builder().data(cars).build();

        when(console.readLine()).thenReturn("0");
        when(router.handleRequest(any())).thenReturn(response);

        // Вызываем тестируемый метод
        handler.handleAndGetState();

        // Проверяем, что были вызваны соответствующие методы
        verify(console).writeLine(view.toString());
        verify(console).writeLine("3. Exit");
        verify(console).readLine();
        verify(router).handleRequest(any());
    }

    // Аналогичные тесты для других опций меню (1, 2, 3)
}