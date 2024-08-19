package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Car;
import io.ylab.backend.domain.model.CarStatus;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.SortOrder;
import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.management.version.StorageVersion;
import io.ylab.storage.in.memory.management.version.StorageVersionManager;
import io.ylab.storage.in.memory.storage.InMemoryCarStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryCarRepositoryTest {
    private InMemoryCarRepository carRepository;

    public static class TestCarStorageVersion extends TestStorageVersion {

        public TestCarStorageVersion(StorageManager storageManager) {
            super(storageManager);
        }

        @Override
        public void upgrade(StorageManager storageManager) {
            storageManager.addStorage("Car", new InMemoryCarStorage());
        }
    }

    @BeforeEach
    void setUp() {
        final StorageManager storageManager = new StorageManager();
        final StorageVersion version = new TestCarStorageVersion(storageManager);

        new StorageVersionManager(storageManager)
                .addVersion(version)
                .initializeStorage();

        carRepository = new InMemoryCarRepository(storageManager);
    }

    @Test
    void findAll_shouldReturnAllCars() {
        carRepository.save(Car.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2022).price(25000)
                .status(CarStatus.AVAILABLE)
                .build()
        );
        carRepository.save(Car.builder()
                .brand("Honda")
                .model("Civic")
                .year(2023)
                .price(20000)
                .status(CarStatus.AVAILABLE)
                .build()
        );

        final List<Car> cars = carRepository.findAll();

        assertEquals(2, cars.size());
    }

    @Test
    void findAll_withPagination_shouldReturnPaginatedCars() {
        carRepository.save(Car.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .price(25000)
                .status(CarStatus.AVAILABLE)
                .build()
        );
        carRepository.save(Car.builder()
                .brand("Honda")
                .model("Civic")
                .year(2023)
                .price(20000)
                .status(CarStatus.AVAILABLE)
                .build()
        );
        carRepository.save(Car.builder()
                .brand("Ford")
                .model("Mustang")
                .year(2021)
                .price(35000)
                .status(CarStatus.AVAILABLE)
                .build()
        );
        final Pagination pagination = new Pagination(1, 2);

        final List<Car> cars = carRepository.findAll(pagination, null);


        assertEquals(2, cars.size());
    }

    @Test
    void findAll_withSorting_shouldReturnSortedCars() {
        carRepository.save(Car.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .price(25000)
                .status(CarStatus.AVAILABLE)
                .build());
        carRepository.save(Car.builder()
                .brand("Honda")
                .model("Civic")
                .year(2023)
                .price(20000)
                .status(CarStatus.AVAILABLE)
                .build()
        );
        carRepository.save(Car.builder()
                .brand("Ford")
                .model("Mustang")
                .year(2021)
                .price(35000)
                .status(CarStatus.AVAILABLE)
                .build());
        final Sorting sorting = new Sorting("brand", SortOrder.ASC); // Сортировка по бренду по возрастанию

        final List<Car> cars = carRepository.findAll(null, sorting);

        assertAll(
                () -> assertEquals("Ford", cars.get(0).getBrand()),
                () -> assertEquals("Honda", cars.get(1).getBrand()),
                () -> assertEquals("Toyota", cars.get(2).getBrand())
        );
    }

    @Test
    void findAll_withPaginationAndSorting_shouldReturnPaginatedAndSortedCars() {
        carRepository.save(Car.builder()
                .brand("Toyota")
                .model("Corolla")
                .year(2022)
                .price(25000)
                .status(CarStatus.AVAILABLE)
                .build()
        );
        carRepository.save(Car.builder()
                .brand("Honda")
                .model("Civic")
                .year(2023)
                .price(20000)
                .status(CarStatus.AVAILABLE)
                .build()
        );
        carRepository.save(Car.builder()
                .brand("Ford")
                .model("Mustang")
                .year(2021)
                .price(35000)
                .status(CarStatus.AVAILABLE)
                .build()
        );
        carRepository.save(Car.builder()
                .brand("Audi")
                .model("A4")
                .year(2020)
                .price(28000)
                .status(CarStatus.AVAILABLE)
                .build()
        );
        final Pagination pagination = new Pagination(2, 2);
        final Sorting sorting = new Sorting("brand", SortOrder.ASC);

        final List<Car> cars = carRepository.findAll(pagination, sorting);

        assertAll(
                () -> assertEquals(2, cars.size()),
                () -> assertEquals("Honda", cars.get(0).getBrand()),
                () -> assertEquals("Toyota", cars.get(1).getBrand())
        );
    }

    @Test
    void findById_existingCar_shouldReturnCar() {
        final Car car = Car.builder()
                .brand("BMW")
                .model("X5")
                .year(2023)
                .price(60000)
                .status(CarStatus.AVAILABLE)
                .build();
        final Car savedCar = carRepository.save(car);

        final Optional<Car> foundCar = carRepository.findById(savedCar.getId());

        assertAll(
                () -> assertTrue(foundCar.isPresent()),
                () -> assertEquals(savedCar, foundCar.get())
        );
    }

    @Test
    void findById_nonExistingCar_shouldReturnEmptyOptional() {
        final Optional<Car> foundCar = carRepository.findById(1L);

        assertFalse(foundCar.isPresent());
    }

    @Test
    void save_shouldSaveCar() {
        final Car car = Car.builder()
                .brand("Mercedes")
                .model("S-Class")
                .year(2022)
                .price(80000)
                .status(CarStatus.AVAILABLE)
                .build();
        final Car savedCar = carRepository.save(car);
        final Optional<Car> foundCar = carRepository.findById(savedCar.getId());

        assertAll(
                () -> assertNotNull(foundCar.get().getId()),
                () -> assertTrue(foundCar.isPresent()),
                () -> assertEquals(savedCar, foundCar.get())
        );
    }

    @Test
    void deleteById_existingCar_shouldDeleteCar() {
        final Car car = Car.builder()
                .brand("Tesla")
                .model("Model S")
                .year(2023)
                .price(75000)
                .status(CarStatus.AVAILABLE)
                .build();
        final Car savedCar = carRepository.save(car);

        carRepository.deleteById(savedCar.getId());
        final Optional<Car> deletedCar = carRepository.findById(savedCar.getId());

        assertFalse(deletedCar.isPresent());
    }
}