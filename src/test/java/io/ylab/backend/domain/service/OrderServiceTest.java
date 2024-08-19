package io.ylab.backend.domain.service;

import io.ylab.backend.domain.model.Order;
import io.ylab.backend.domain.model.OrderStatus;
import io.ylab.backend.domain.model.OrderType;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.backend.infrastructure.repository.OrderRepository;
import io.ylab.common.request.filter.ComparisonOperator;
import io.ylab.common.request.filter.FilterCriterion;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class OrderServiceTest {

    @Test
    void findAll_withFiltering_shouldReturnFilteredOrders() {
        final OrderRepository orderRepository = mock(OrderRepository.class);
        final OrderService orderService = new OrderService(orderRepository);
        final List<FilterCriterion<?>> criteria = Arrays.asList(
                new FilterCriterion<>("userId", ComparisonOperator.EQUALS, 1L),
                new FilterCriterion<>("status", ComparisonOperator.EQUALS, OrderStatus.NEW)
        );
        when(orderRepository.findAll(any(), any()))
                .thenReturn(Arrays.asList(
                                Order.builder()
                                        .id(1L)
                                        .userId(1L)
                                        .carId(10L)
                                        .quantity(1)
                                        .status(OrderStatus.NEW)
                                        .type(OrderType.PURCHASE)
                                        .createdAt(LocalDateTime.now())
                                        .updatedAt(LocalDateTime.now())
                                        .build(),
                                Order.builder()
                                        .id(2L)
                                        .userId(2L)
                                        .carId(20L)
                                        .quantity(2)
                                        .status(OrderStatus.PROCESSING)
                                        .type(OrderType.SERVICE)
                                        .createdAt(LocalDateTime.now().plusDays(1))
                                        .updatedAt(LocalDateTime.now().plusDays(1))
                                        .build(),
                                Order.builder()
                                        .id(3L)
                                        .userId(1L)
                                        .carId(30L)
                                        .quantity(3)
                                        .status(OrderStatus.COMPLETED)
                                        .type(OrderType.PURCHASE)
                                        .createdAt(LocalDateTime.now().plusDays(2))
                                        .updatedAt(LocalDateTime.now().plusDays(2))
                                        .build()
                        )
                );

        final List<Order> filteredOrders = orderService.findAll(null, null, criteria);

        assertAll(
                () -> assertEquals(1, filteredOrders.size()),
                () -> assertEquals(1L, filteredOrders.get(0).getUserId()),
                () -> assertEquals(OrderStatus.NEW, filteredOrders.get(0).getStatus())
        );
    }

    @Test
    void findAll_withPagination_shouldReturnPaginatedOrders() {
        final OrderRepository orderRepository = mock(OrderRepository.class);
        final OrderService orderService = new OrderService(orderRepository);
        when(orderRepository.findAll(nullable(Pagination.class), nullable(Sorting.class)))
                .thenReturn(Arrays.asList(
                        Order.builder()
                                .id(1L)
                                .userId(1L)
                                .carId(10L)
                                .quantity(1)
                                .status(OrderStatus.NEW)
                                .type(OrderType.PURCHASE)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build(),
                        Order.builder()
                                .id(2L)
                                .userId(2L)
                                .carId(20L)
                                .quantity(2)
                                .status(OrderStatus.PROCESSING)
                                .type(OrderType.SERVICE)
                                .createdAt(LocalDateTime.now().plusDays(1))
                                .updatedAt(LocalDateTime.now().plusDays(1))
                                .build()
                ));
        final List<Order> paginatedCars = orderService.findAll(null, null, Collections.emptyList());

        assertEquals(2, paginatedCars.size());
    }

    @Test
    void findAll_withSorting_shouldReturnSortedOrders() {
        final OrderRepository orderRepository = mock(OrderRepository.class);
        final OrderService orderService = new OrderService(orderRepository);
        when(orderRepository.findAll(nullable(Pagination.class), nullable(Sorting.class)))
                .thenReturn(Arrays.asList(
                        Order.builder()
                                .id(2L)
                                .userId(2L)
                                .carId(20L)
                                .quantity(2)
                                .status(OrderStatus.PROCESSING)
                                .type(OrderType.SERVICE)
                                .createdAt(LocalDateTime.now().plusDays(1))
                                .updatedAt(LocalDateTime.now().plusDays(1))
                                .build(),
                        Order.builder()
                                .id(1L)
                                .userId(1L)
                                .carId(10L)
                                .quantity(1)
                                .status(OrderStatus.NEW)
                                .type(OrderType.PURCHASE)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build()
                ));
        final List<Order> sortedCars = orderService.findAll(
                null, null, Collections.emptyList()
        );

        assertAll(
                () -> assertEquals(2, sortedCars.size()),
                () -> assertEquals(2L, sortedCars.get(0).getUserId()),
                () -> assertEquals(1L, sortedCars.get(1).getUserId())
        );
    }

    @Test
    void update_existingOrder_shouldUpdateOrder() {
        final OrderRepository orderRepository = mock(OrderRepository.class);
        final OrderService orderService = new OrderService(orderRepository);
        final Order existingOrder = Order.builder()
                .id(1L)
                .userId(1L)
                .carId(10L)
                .quantity(1)
                .status(OrderStatus.NEW)
                .type(OrderType.PURCHASE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        final Order updatedOrder = Order.builder()
                .id(1L)
                .userId(2L)
                .carId(20L)
                .quantity(2)
                .status(OrderStatus.PROCESSING)
                .type(OrderType.SERVICE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        when(orderRepository.findById(1L)).thenReturn(Optional.of(existingOrder));
        when(orderRepository.save(updatedOrder)).thenReturn(updatedOrder);

        final Order result = orderService.update(updatedOrder);

        assertEquals(updatedOrder, result);
        verify(orderRepository).save(updatedOrder);
    }

    @Test
    void update_nonExistingOrder_shouldThrowException() {
        final OrderRepository orderRepository = mock(OrderRepository.class);
        final Order nonExistingOrder = Order.builder()
                .id(999L)
                .userId(1L)
                .carId(10L)
                .quantity(1)
                .status(OrderStatus.NEW)
                .type(OrderType.PURCHASE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        final OrderService orderService = new OrderService(orderRepository);
        when(orderRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> orderService.update(nonExistingOrder));
        verify(orderRepository, never()).save(any());
    }

    @Test
    void create_shouldSaveOrderAndLogAction() {
        final Order order = Order.builder()
                .userId(1L)
                .carId(10L)
                .quantity(1)
                .status(OrderStatus.NEW)
                .type(OrderType.PURCHASE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        final OrderRepository orderRepository = mock(OrderRepository.class);
        final OrderService orderService = new OrderService(orderRepository);
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        final Order createdOrder = orderService.create(order);

        assertEquals(order, createdOrder);
        verify(orderRepository).save(order);
    }

    @Test
    void deleteById_shouldDeleteOrderAndLogAction() {
        final OrderRepository orderRepository = mock(OrderRepository.class);
        final OrderService orderService = new OrderService(orderRepository);
        final Long carId = 1L;

        orderService.deleteById(carId);

        verify(orderRepository).deleteById(carId);
    }

}