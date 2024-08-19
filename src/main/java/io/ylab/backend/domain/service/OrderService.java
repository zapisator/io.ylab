package io.ylab.backend.domain.service;

import io.ylab.backend.domain.model.Order;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.backend.domain.service.search.ReflectiveSpecification;
import io.ylab.backend.domain.service.search.Specification;
import io.ylab.backend.infrastructure.repository.OrderRepository;
import io.ylab.common.request.filter.FilterCriterion;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class OrderService implements FindAllWithPaginationSortingAndSpecificationService<Order, Long> {
    private final OrderRepository orderRepository;

    @Override
    public List<Order> findAll(
            Pagination pagination, Sorting sorting,
            List<FilterCriterion<?>> criteria
    ) {
        final List<Order> cars = orderRepository.findAll(pagination, sorting);
        final Specification<Order> carSpecification = ReflectiveSpecification.of(Order.class, criteria);
        return carSpecification.apply(cars);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public Order create(Order order) {
        return orderRepository.save(order);
    }

    @Override
    public Order update(Order order) {
        if (!orderRepository.findById(order.getId()).isPresent()) {
            throw new IllegalArgumentException("Order with ID " + order.getId() + " not found");
        }

        return orderRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        orderRepository.deleteById(id);
    }
}