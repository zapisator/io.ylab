package io.ylab.backend.domain.service;

import io.ylab.backend.domain.model.Car;
import io.ylab.backend.domain.model.CarStatus;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.backend.infrastructure.repository.CarRepository;
import io.ylab.common.request.SortOrder;
import io.ylab.common.request.filter.ComparisonOperator;
import io.ylab.common.request.filter.FilterCriterion;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarServiceTest {

    @Test
    void findAll_withFiltering_shouldReturnFilteredCars() {
        final CarRepository carRepository = mock(CarRepository.class);
        final CarService carService = new CarService(carRepository);
        final List<FilterCriterion<?>> criteria = Arrays.asList(
                new FilterCriterion<>("brand", ComparisonOperator.EQUALS, "Toyota"),
                new FilterCriterion<>("status", ComparisonOperator.EQUALS, CarStatus.AVAILABLE)
        );
        when(carRepository.findAll(any(), any()))
                .thenReturn(Arrays.asList(
                                Car.builder()
                                        .id(1L)
                                        .brand("Toyota")
                                        .model("Corolla")
                                        .year(2022)
                                        .price(25000)
                                        .status(CarStatus.AVAILABLE)
                                        .build(),
                                Car.builder()
                                        .id(2L)
                                        .brand("Honda")
                                        .model("Civic")
                                        .year(2023)
                                        .price(20000)
                                        .status(CarStatus.AVAILABLE)
                                        .build(),
                                Car.builder()
                                        .id(3L)
                                        .brand("Ford")
                                        .model("Mustang")
                                        .year(2021)
                                        .price(35000)
                                        .status(CarStatus.SOLD)
                                        .build()
                        )
                );

        final List<Car> filteredCars = carService.findAll(null, null, criteria);

        assertAll(
                () -> assertEquals(1, filteredCars.size()),
                () -> assertEquals("Toyota", filteredCars.get(0).getBrand()),
                () -> assertEquals(CarStatus.AVAILABLE, filteredCars.get(0).getStatus())
        );
    }

    @Test
    void findAll_withPagination_shouldReturnPaginatedCars() {
        final CarRepository carRepository = mock(CarRepository.class);
        final CarService carService = new CarService(carRepository);
        when(carRepository.findAll(nullable(Pagination.class), nullable(Sorting.class)))
                .thenReturn(Arrays.asList(
                        Car.builder()
                                .id(1L)
                                .brand("Toyota")
                                .model("Corolla")
                                .year(2022)
                                .price(25000)
                                .status(CarStatus.AVAILABLE)
                                .build(),
                        Car.builder()
                                .id(2L)
                                .brand("Honda")
                                .model("Civic")
                                .year(2023)
                                .price(20000
                                ).status(CarStatus.AVAILABLE)
                                .build()
                ));
        final List<Car> paginatedCars = carService.findAll(null, null, Collections.emptyList());

        assertEquals(2, paginatedCars.size());
    }

    @Test
    void findAll_withSorting_shouldReturnSortedCars() {
        final CarRepository carRepository = mock(CarRepository.class);
        final CarService carService = new CarService(carRepository);
        when(carRepository.findAll(nullable(Pagination.class), nullable(Sorting.class)))
                .thenReturn(Arrays.asList(
                        Car.builder()
                                .id(1L)
                                .brand("Honda")
                                .model("Civic")
                                .year(2023)
                                .price(20000)
                                .status(CarStatus.AVAILABLE)
                                .build(),
                        Car.builder()
                                .id(2L)
                                .brand("Toyota")
                                .model("Corolla")
                                .year(2022)
                                .price(25000)
                                .status(CarStatus.AVAILABLE)
                                .build()
                ));

        final List<Car> sortedCars = carService.findAll(
                null, null, Collections.emptyList()
        );

        assertAll(
                () -> assertEquals(2, sortedCars.size()),
                () -> assertEquals("Honda", sortedCars.get(0).getBrand()),
                () -> assertEquals("Toyota", sortedCars.get(1).getBrand())
        );
    }

    @Test
    void update_existingCar_shouldUpdateCar() {
        final CarRepository carRepository = mock(CarRepository.class);
        final CarService carService = new CarService(carRepository);
        final Car existingCar = Car.builder()
                .id(1L)
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .price(25000)
                .status(CarStatus.AVAILABLE)
                .build();
        final Car updatedCar = Car
                .builder()
                .id(1L)
                .brand("Toyota")
                .model("Corolla")
                .year(2023)
                .price(26000)
                .status(CarStatus.AVAILABLE)
                .build();
        when(carRepository.findById(1L)).thenReturn(Optional.of(existingCar));
        when(carRepository.save(updatedCar)).thenReturn(updatedCar);

        final Car result = carService.update(updatedCar);

        assertEquals(updatedCar, result);
        verify(carRepository).save(updatedCar);
    }

    @Test
    void update_nonExistingCar_shouldThrowException() {
        final CarRepository carRepository = mock(CarRepository.class);
        final Car nonExistingCar = Car.builder()
                .id(999L)
                .brand("Test")
                .model("Test")
                .year(2023)
                .price(10000)
                .status(CarStatus.AVAILABLE)
                .build();
        final CarService carService = new CarService(carRepository);
        when(carRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> carService.update(nonExistingCar));
        verify(carRepository, never()).save(any());
    }

    @Test
    void create_shouldSaveCarAndLogAction() {
        final Car car = Car.builder()
                .brand("Tesla")
                .model("Model 3")
                .year(2023)
                .price(50000)
                .status(CarStatus.AVAILABLE)
                .build();
        final CarRepository carRepository = mock(CarRepository.class);
        final CarService carService = new CarService(carRepository);
        when(carRepository.save(any(Car.class))).thenReturn(car);

        final Car createdCar = carService.create(car);

        assertEquals(car, createdCar);
        verify(carRepository).save(car);
    }

    @Test
    void deleteById_shouldDeleteCarAndLogAction() {
        final CarRepository carRepository = mock(CarRepository.class);
        final CarService carService = new CarService(carRepository);
        final Long carId = 1L;

        carService.deleteById(carId);

        verify(carRepository).deleteById(carId);
    }
}