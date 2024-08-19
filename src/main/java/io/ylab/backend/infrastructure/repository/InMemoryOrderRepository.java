package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Order;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.storage.in.memory.dto.OrderDto;
import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.storage.Storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryOrderRepository implements OrderRepository{
    private final StorageManager storageManager;

    public InMemoryOrderRepository(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    private Storage<OrderDto, Long> orderStorage() {
        return storageManager.getStorage("Order");
    }

    @Override
    public List<Order> findAll() {
        return orderStorage().findAll().stream()
                .map(this::toOrder)
                .collect(Collectors.toList());
    }

    @Override
    public List<Order> findAll(Pagination pagination, Sorting sorting) {
        return orderStorage().findAll(sorting, pagination).stream()
                .map(this::toOrder)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Order> findById(Long id) {
        return orderStorage().findById(id)
                .map(this::toOrder);
    }

    @Override
    public Order save(Order order) {
        final OrderDto orderDto = toOrderDto(order);
        orderStorage().save(orderDto);
        return toOrder(orderDto);
    }

    @Override
    public void deleteById(Long id) {
        orderStorage().deleteById(id);
    }

    private OrderDto toOrderDto(final Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .carId(order.getCarId())
                .quantity(order.getQuantity())
                .status(order.getStatus())
                .type(order.getType())
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }

    private Order toOrder(final OrderDto orderDto) {
        return Order.builder()
                .id(orderDto.getId())
                .userId(orderDto.getUserId())
                .carId(orderDto.getCarId())
                .quantity(orderDto.getQuantity())
                .status(orderDto.getStatus())
                .type(orderDto.getType())
                .createdAt(orderDto.getCreatedAt())
                .updatedAt(orderDto.getUpdatedAt())
                .build();
    }
}
