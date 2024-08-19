package io.ylab.backend.infrastructure.rest.controller;

import io.ylab.backend.domain.service.CarService;
import io.ylab.common.annotation.RequestMapping;
import io.ylab.common.request.Request;
import io.ylab.common.response.DefaultResponse;
import io.ylab.common.response.Response;
import io.ylab.common.annotation.Endpoint;
import io.ylab.backend.domain.model.Car;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;

import static io.ylab.common.authorization.UserRole.ADMIN;
import static io.ylab.common.authorization.UserRole.CLIENT;
import static io.ylab.common.authorization.UserRole.MANAGER;
import static io.ylab.common.method.MethodType.DELETE;
import static io.ylab.common.method.MethodType.GET;
import static io.ylab.common.method.MethodType.OPTIONS;
import static io.ylab.common.method.MethodType.POST;
import static io.ylab.common.method.MethodType.PUT;

/**
 * Контроллер, отвечающий за обработку запросов,
 * связанных с автомобилями.
 */
@RequestMapping("/cars")
@RequiredArgsConstructor
public class CarController implements CrudController<Car> {
    /**
     * Сервис для работы с автомобилями.
     */
    private final CarService carService;

    /**
     * Обрабатывает OPTIONS-запрос для получения меню,
     * связанного с автомобилями.
     *
     * @return Ответ, содержащий опции меню.
     */
    @Override
    @Endpoint(method = OPTIONS, roles = {ADMIN, MANAGER, CLIENT})
    public Response<List<String>> menu() {
        return DefaultResponse.<List<String>>builder()
                .message("menu")
                .data(Collections.singletonList("menu"))
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает POST-запрос для создания нового автомобиля.
     *
     * @param request Запрос, содержащий информацию о новом автомобиле.
     * @return Ответ, содержащий информацию о созданном автомобиле.
     */
    @Override
    @Endpoint(method = POST, roles = {ADMIN})
    public Response<Car> createOne(Request request) {
        return DefaultResponse.<Car>builder()
                .message("createOne")
                .data(Car.builder().build())
                .dataCanonicalClassName(Car.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает GET-запрос для получения списка всех автомобилей.
     *
     * @param request Запрос.
     * @return Ответ, содержащий список всех автомобилей.
     */
    @Override
    @Endpoint(method = GET, roles = {ADMIN})
    public Response<List<Car>> readMany(Request request) {
        final List<Car> allCars = carService.getAllCars();

        return DefaultResponse.<List<Car>>builder()
                .message("List of all cars")
                .data(allCars)
                .dataCanonicalClassName(List.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает GET-запрос для получения информации о конкретном автомобиле.
     *
     * @param request Запрос, содержащий ID автомобиля.
     * @return Ответ, содержащий информацию о запрошенном автомобиле.
     */
    @Override
    @Endpoint(method = GET, path = "/{carId}", isTemplate = true, roles = {ADMIN})
    public Response<Car> readOne(Request request) {
        return DefaultResponse.<Car>builder()
                .message("seeOne")
                .data(Car.builder().build())
                .dataCanonicalClassName(Car.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает PUT-запрос для обновления информации о конкретном автомобиле.
     *
     * @param request Запрос, содержащий ID автомобиля и обновленную информацию.
     * @return Ответ, содержащий информацию об обновленном автомобиле.
     */
    @Override
    @Endpoint(method = PUT, path = "/{carId}", roles = {ADMIN})
    public Response<Car> updateOne(Request request) {
        return DefaultResponse.<Car>builder()
                .message("editOne")
                .data(Car.builder().build())
                .dataCanonicalClassName(Car.class.getCanonicalName())
                .build();
    }

    /**
     * Обрабатывает DELETE-запрос для удаления конкретного автомобиля.
     *
     * @param request Запрос, содержащий ID автомобиля.
     * @return Ответ, содержащий информацию об удаленном автомобиле.
     */
    @Override
    @Endpoint(method = DELETE, path = "/{carId}", isTemplate = true, roles = {CLIENT})
    public Response<Car> deleteOne(Request request) {
        return DefaultResponse.<Car>builder()
                .message("deleteOne")
                .data(Car.builder().build())
                .dataCanonicalClassName(Car.class.getCanonicalName())
                .build();
    }
}
