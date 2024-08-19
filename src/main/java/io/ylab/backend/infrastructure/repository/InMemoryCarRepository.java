package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Car;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.storage.in.memory.dto.CarDto;
import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.storage.Storage;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InMemoryCarRepository implements CarRepository {

    private final StorageManager storageManager;

    private Storage<CarDto, Long> carStorage() {
        return storageManager.getStorage("Car");
    }

    @Override
    public List<Car> findAll() {
        return carStorage().findAll().stream()
                .map(this::toCar)
                .collect(Collectors.toList());
    }

    @Override
    public List<Car> findAll(Pagination pagination, Sorting sorting) {
        return carStorage().findAll(sorting, pagination).stream()
                .map(this::toCar)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carStorage().findById(id)
                .map(this::toCar);
    }

    @Override
    public Car save(Car car) {
        final CarDto carDto = toCarDto(car);
        carStorage().save(carDto);
        return toCar(carDto);
    }

    @Override
    public void deleteById(Long id) {
        carStorage().deleteById(id);
    }

    private CarDto toCarDto(final Car car) {
        return CarDto.builder()
                .id(car.getId())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .price(car.getPrice())
                .status(car.getStatus())
                .build();
    }

    private Car toCar(final CarDto carDto) {
        return Car.builder()
                .id(carDto.getId())
                .brand(carDto.getBrand())
                .model(carDto.getModel())
                .year(carDto.getYear())
                .price(carDto.getPrice())
                .status(carDto.getStatus())
                .build();
    }
}