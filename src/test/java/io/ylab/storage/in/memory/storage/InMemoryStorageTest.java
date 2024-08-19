package io.ylab.storage.in.memory.storage;

import io.ylab.backend.domain.model.CarStatus;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.SortOrder;
import io.ylab.storage.in.memory.dto.CarDto;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryStorageTest {
    @Test
    void findAll_pagination_returnsPaginatedResults() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();
        IntStream.range(1, 101)
                .forEach(i -> storage.save(
                        CarDto.builder()
                                .brand("Brand" + i)
                                .model("Model" + i)
                                .price(i * 1000.0)
                                .year(2020 + i % 5)
                                .status(i % 2 == 0 ? CarStatus.AVAILABLE : CarStatus.SOLD)
                                .build()
                ));

        final Pagination pagination = new Pagination(3, 20);

        final List<CarDto> result = storage.findAll(pagination);

        assertAll(
                () -> assertEquals(20, result.size()),
                () -> assertEquals(41L, result.get(0).getId()),
                () -> assertEquals(60L, result.get(19).getId())
        );
    }

    @Test
    void findAll_sorting_returnsSortedResults() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();
        storage.save(CarDto.builder()
                .brand("Toyota")
                .model("Camry")
                .year(2022)
                .price(30000
                ).status(CarStatus.AVAILABLE)
                .build());
        storage.save(CarDto.builder()
                .brand("Honda")
                .model("Accord")
                .year(2021)
                .price(25000)
                .status(CarStatus.AVAILABLE)
                .build());
        storage.save(CarDto.builder()
                .brand("Ford")
                .model("Mustang")
                .year(2023)
                .price(40000)
                .status(CarStatus.AVAILABLE)
                .build());
        final Sorting sorting = new Sorting("price", SortOrder.ASC);

        final List<CarDto> result = storage.findAll(sorting);

        assertAll(
                () -> assertEquals("Honda", result.get(0).getBrand()),
                () -> assertEquals("Toyota", result.get(1).getBrand()),
                () -> assertEquals("Ford", result.get(2).getBrand())
        );
    }

    @Test
    void findAll_sortingAndPagination_returnsSortedAndPaginatedResults() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();
        storage.save(CarDto.builder()
                .brand("Toyota")
                .model("Camry")
                .year(2022)
                .price(30000)
                .status(CarStatus.AVAILABLE)
                .build());
        storage.save(CarDto.builder()
                .brand("Honda")
                .model("Accord")
                .year(2021)
                .price(25000)
                .status(CarStatus.AVAILABLE)
                .build());
        storage.save(CarDto.builder()
                .brand("Ford")
                .model("Mustang")
                .year(2023)
                .price(40000)
                .status(CarStatus.AVAILABLE)
                .build());
        storage.save(CarDto.builder()
                .brand("Audi")
                .model("A4")
                .year(2020)
                .price(28000)
                .status(CarStatus.AVAILABLE)
                .build());
        final Sorting sorting = new Sorting("price", SortOrder.ASC);
        final Pagination pagination = new Pagination(1, 2);

        final List<CarDto> result = storage.findAll(sorting, pagination);

        assertAll(
                () -> assertEquals(2, result.size()),
                () -> assertEquals("Honda", result.get(0).getBrand()),
                () -> assertEquals("Audi", result.get(1).getBrand())
        );
    }

    @Test
    void findAll_emptyStorage_returnsEmptyList() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();

        assertTrue(storage.findAll().isEmpty());
    }

    @Test
    void findById_existingId_returnsOptionalWithEntity() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();
        final CarDto car = CarDto.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .price(25000)
                .status(CarStatus.AVAILABLE)
                .build();
        storage.save(car);

        final Optional<CarDto> result = storage.findById(car.getId());

        assertTrue(result.isPresent());
        assertEquals(car, result.get());
    }

    @Test
    void findById_nonExistingId_returnsEmptyOptional() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();
        final Optional<CarDto> result = storage.findById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    void save_newEntity_assignsIdAndSaves() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();
        final CarDto car = CarDto.builder()
                .brand("Honda")
                .model("Civic")
                .year(2023)
                .price(22000)
                .status(CarStatus.AVAILABLE)
                .build();

        storage.save(car);

        final CarDto savedCar = storage.findById(1L).get();
        assertAll(
                () -> assertNotNull(savedCar.getId()),
                () -> assertEquals(1L, savedCar.getId()),
                () -> assertEquals("Honda", savedCar.getBrand()),
                () -> assertEquals("Civic", savedCar.getModel())
        );
    }

    @Test
    void save_existingEntity_updatesEntity() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();
        final CarDto car = CarDto.builder()
                .brand("Honda")
                .model("Civic")
                .year(2023)
                .price(22000)
                .status(CarStatus.AVAILABLE)
                .build();
        final CarDto updatedCar = CarDto.builder()
                .id(car.getId())
                .brand("Updated Brand")
                .model("Updated Model")
                .year(2024)
                .price(25000)
                .status(CarStatus.SOLD)
                .build();
        final long id = storage.save(car);
        updatedCar.setId(id);
        storage.save(updatedCar);

        final CarDto savedCar = storage.findById(car.getId()).get();
        assertAll(
                () -> assertEquals(updatedCar, savedCar),
                () -> assertEquals("Updated Brand", savedCar.getBrand()),
                () -> assertEquals("Updated Model", savedCar.getModel()),
                () -> assertEquals(2024, savedCar.getYear())
        );
    }

    @Test
    void deleteById_existingId_removesEntity() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();
        final CarDto car = CarDto.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .price(25000)
                .status(CarStatus.AVAILABLE)
                .build();
        storage.save(car);

        storage.deleteById(car.getId());

        assertFalse(storage.findById(car.getId()).isPresent());
    }

    @Test
    void deleteById_nonExistingId_doesNothing() {
        final InMemoryCarStorage storage = new InMemoryCarStorage();
        storage.deleteById(1L);
        assertTrue(storage.findAll().isEmpty());
    }
}