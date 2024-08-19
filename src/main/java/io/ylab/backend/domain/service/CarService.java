package io.ylab.backend.domain.service;

import io.ylab.backend.domain.model.Car;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.backend.domain.service.search.ReflectiveSpecification;
import io.ylab.backend.domain.service.search.Specification;
import io.ylab.backend.infrastructure.repository.CarRepository;
import io.ylab.common.request.filter.FilterCriterion;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CarService implements FindAllWithPaginationSortingAndSpecificationService<Car, Long> {
    private final CarRepository carRepository;

    @Override
    public List<Car> findAll(
            Pagination pagination, Sorting sorting, List<FilterCriterion<?>> criteria
    ) {
        final List<Car> cars = carRepository.findAll(pagination, sorting);
        final Specification<Car> carSpecification = ReflectiveSpecification.of(Car.class, criteria);
        return carSpecification.apply(cars);
    }

    @Override
    public Optional<Car> findById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    public Car create(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car update(Car car) {
        if (!carRepository.findById(car.getId()).isPresent()) {
            throw new IllegalArgumentException("Car with ID " + car.getId() + " not found");
        }

        return carRepository.save(car);
    }

    @Override
    public void deleteById(Long id) {
        carRepository.deleteById(id);
    }

}