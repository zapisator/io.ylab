package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Car;
import io.ylab.backend.domain.model.CarStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Реализация репозитория автомобилей,
 * хранящая данные в памяти в виде коллекции.
 */
public class InMemoryCarRepository implements CarRepository {

    /**
     * Список автомобилей,  хранящихся в памяти.
     */
    private final List<Car> cars = new ArrayList<>();

    /**
     * Конструктор репозитория.
     * Создает 10 автомобилей с различными данными и  добавляет  их  в  список.
     */
    public InMemoryCarRepository() {
        // Предзаполнение 10 автомобилями
        for (int i = 1; i <= 10; i++) {
            cars.add(Car.builder()
                    .id((long) i)
                    .brand("Brand" + i)
                    .model("Model" + i)
                    .year(2020 + i)
                    .price(10000 * i)
                    .status(CarStatus.AVAILABLE)
                    .build());
        }
    }

    /**
     * Возвращает список всех автомобилей.
     *
     * @return Список всех автомобилей (копия списка,  хранящегося в памяти).
     */
    @Override
    public List<Car> findAll() {
        return new ArrayList<>(cars);
    }
}
