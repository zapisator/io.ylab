package io.ylab.backend.domain.service;

import io.ylab.backend.domain.model.Car;
import io.ylab.backend.infrastructure.repository.CarRepository;

import java.util.List;

/**
 * Сервис для работы с автомобилями.
 */
public class CarService {

    /**
     * Репозиторий автомобилей.
     */
    private final CarRepository carRepository;

    /**
     * Конструктор сервиса.
     *
     * @param carRepository Репозиторий автомобилей.
     */
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    /**
     * Возвращает список всех автомобилей.
     *
     * @return Список автомобилей.
     */
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }
}
