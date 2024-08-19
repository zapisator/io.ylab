package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Order;
import io.ylab.backend.domain.model.OrderStatus;
import io.ylab.backend.domain.model.OrderType;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.SortOrder;
import io.ylab.storage.in.memory.dto.OrderDto;
import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.management.version.StorageVersion;
import io.ylab.storage.in.memory.management.version.StorageVersionManager;
import io.ylab.storage.in.memory.storage.InMemoryOrderStorage;
import io.ylab.storage.in.memory.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryOrderRepositoryTest {
    private static final String STORAGE_NAME = "Order";
    private InMemoryOrderRepository orderRepository;
    private Storage<OrderDto, Long> orderStorage;

    public static class TestOrderStorageVersion extends TestStorageVersion {

        public TestOrderStorageVersion(StorageManager storageManager) {
            super(storageManager);
        }

        @Override
        public void upgrade(StorageManager storageManager) {
            storageManager.addStorage(STORAGE_NAME, new InMemoryOrderStorage());
        }
    }

    @BeforeEach
    void setUp() {
        final StorageManager storageManager = new StorageManager();
        final StorageVersion version = new TestOrderStorageVersion(storageManager);

        this.orderStorage = new StorageVersionManager(storageManager)
                .addVersion(version)
                .initializeStorage()
                .getStorage(STORAGE_NAME);

        orderRepository = new InMemoryOrderRepository(storageManager);
    }

    @AfterEach
    void tearDown() {
        final List<OrderDto> orders = orderStorage.findAll();
        for (final OrderDto order : orders) {
            orderStorage.deleteById(order.getId());
        }
    }

    @Test
    void findAll_shouldReturnAllOrders() {
        final Order order1 = Order.builder()
                .userId(1L)
                .carId(10L)
                .quantity(1)
                .status(OrderStatus.NEW)
                .type(OrderType.PURCHASE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        final Order order2 = Order.builder()
                .userId(2L)
                .carId(20L)
                .quantity(2)
                .status(OrderStatus.PROCESSING)
                .type(OrderType.SERVICE)
                .createdAt(LocalDateTime.now().plusDays(1))
                .updatedAt(LocalDateTime.now().plusDays(1))
                .build();

        orderRepository.save(order1).getId();
        orderRepository.save(order2).getId();

        final List<Order> orders = orderRepository.findAll();

        assertAll(
                () -> assertEquals(2, orders.size()),
                () -> assertTrue(orders.contains(order1)),
                () -> assertTrue(orders.contains(order2))
        );
    }

    @Test
    void findAll_withPagination_shouldReturnPaginatedOrders() {
        for (int i = 1; i <= 15; i++) {
            final Order order = Order.builder()
                    .userId((long) i)
                    .carId((long) i * 10)
                    .quantity(i)
                    .status(i % 2 == 0 ? OrderStatus.COMPLETED : OrderStatus.NEW)
                    .type(i % 3 == 0 ? OrderType.SERVICE : OrderType.PURCHASE)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            orderRepository.save(order);
        }
        final Pagination pagination = new Pagination(2, 5);

        final List<Order> orders = orderRepository.findAll(pagination, null);

        assertAll(
                () -> assertEquals(5, orders.size()),
                () -> assertEquals(6L, orders.get(0).getId()),
                () -> assertEquals(10L, orders.get(4).getId())
        );
    }

    @Test
    void findAll_withSorting_shouldReturnSortedOrders() {
        final Order order1 = Order.builder()
                .userId(1L)
                .carId(10L)
                .quantity(1)
                .status(OrderStatus.NEW)
                .type(OrderType.PURCHASE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        final Order order2 = Order.builder()
                .userId(2L)
                .carId(20L)
                .quantity(2)
                .status(OrderStatus.PROCESSING)
                .type(OrderType.SERVICE)
                .createdAt(LocalDateTime.now().plusDays(1))
                .updatedAt(LocalDateTime.now().plusDays(1))
                .build();
        final Sorting sorting = new Sorting("userId", SortOrder.DESC); // Sorting by userId in descending order

        orderRepository.save(order1);
        orderRepository.save(order2);

        final List<Order> orders = orderRepository.findAll(null, sorting);

        assertAll(
                () -> assertEquals(2L, orders.get(0).getUserId()),
                () -> assertEquals(1L, orders.get(1).getUserId())
        );
    }

    @Test
    void findAll_withPaginationAndSorting_shouldReturnPaginatedAndSortedOrders() {
        for (int i = 1; i <= 15; i++) {
            final Order order = Order.builder()
                    .userId((long) (i % 3 + 1))
                    .carId((long) i * 10)
                    .quantity(i)
                    .status(i % 2 == 0 ? OrderStatus.COMPLETED : OrderStatus.NEW)
                    .type(i % 3 == 0 ? OrderType.SERVICE : OrderType.PURCHASE)
                    .createdAt(LocalDateTime.now())
                    .updatedAt(LocalDateTime.now())
                    .build();

            orderRepository.save(order);
        }
        final Sorting sorting = new Sorting("userId", SortOrder.ASC);
        final Pagination pagination = new Pagination(2, 5);

        final List<Order> orders = orderRepository.findAll(pagination, sorting);

        assertAll(
                () -> assertEquals(5, orders.size()),
                () -> assertEquals(2L, orders.get(0).getUserId()),
                () -> assertEquals(2L, orders.get(1).getUserId()),
                () -> assertEquals(2L, orders.get(2).getUserId()),
                () -> assertEquals(2L, orders.get(3).getUserId()),
                () -> assertEquals(2L, orders.get(4).getUserId())
        );
    }


    @Test
    void findById_existingOrder_shouldReturnOrder() {
        final Order order = Order.builder()
                .userId(1L)
                .carId(10L)
                .quantity(2)
                .status(OrderStatus.PROCESSING)
                .type(OrderType.PURCHASE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        final Order savedOrder = orderRepository.save(order);

        final Optional<Order> foundOrder = orderRepository.findById(savedOrder.getId());

        assertAll(
                () -> assertTrue(foundOrder.isPresent()),
                () -> assertEquals(savedOrder, foundOrder.get())
        );
    }

    @Test
    void findById_nonExistingOrder_shouldReturnEmptyOptional() {
        final Optional<Order> foundOrder = orderRepository.findById(999L);

        assertFalse(foundOrder.isPresent());
    }

    @Test
    void save_shouldSaveOrderAndGenerateId() {
        final Order order = Order.builder()
                .userId(1L)
                .carId(10L)
                .quantity(1)
                .status(OrderStatus.NEW)
                .type(OrderType.PURCHASE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        final Order savedOrder = orderRepository.save(order);

        assertAll(
                () -> assertNotNull(savedOrder.getId()),
                () -> assertTrue(orderRepository.findById(savedOrder.getId()).isPresent())
        );
    }

    @Test
    void deleteById_existingOrder_shouldDeleteOrder() {
        final Order order = Order.builder()
                .userId(1L)
                .carId(10L)
                .quantity(1)
                .status(OrderStatus.NEW)
                .type(OrderType.PURCHASE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        final Order savedOrder = orderRepository.save(order);

        orderRepository.deleteById(savedOrder.getId());

        assertFalse(orderRepository.findById(savedOrder.getId()).isPresent());
    }
}
