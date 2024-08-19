package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Log;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.SortOrder;
import io.ylab.storage.in.memory.dto.LogDto;
import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.management.version.StorageVersion;
import io.ylab.storage.in.memory.management.version.StorageVersionManager;
import io.ylab.storage.in.memory.storage.InMemoryLogStorage;
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

class InMemoryLogRepositoryTest {
    private static final String STORAGE_NAME = "Log";
    private InMemoryLogRepository logRepository;
    private Storage<LogDto, Long> logStorage;

    public static class TestLogStorageVersion extends TestStorageVersion {

        public TestLogStorageVersion(StorageManager storageManager) {
            super(storageManager);
        }

        @Override
        public void upgrade(StorageManager storageManager) {
            storageManager.addStorage(STORAGE_NAME, new InMemoryLogStorage());
        }
    }

    @BeforeEach
    void setUp() {
        final StorageManager storageManager = new StorageManager();
        final StorageVersion version = new TestLogStorageVersion(storageManager);

        this.logStorage = new StorageVersionManager(storageManager)
                .addVersion(version)
                .initializeStorage()
                .getStorage(STORAGE_NAME);

        logRepository = new InMemoryLogRepository(storageManager);
    }

    @AfterEach
    void tearDown() {
        final List<LogDto> orders = logStorage.findAll();
        for (final LogDto order : orders) {
            logStorage.deleteById(order.getId());
        }
    }

    @Test
    void findAll_shouldReturnAllLogs() {
        final Log log1 = Log.builder()
                .user("user1")
                .action("LOGIN")
                .details("Successful login")
                .timestamp(LocalDateTime.now())
                .build();
        final Log log2 = Log.builder()
                .user("user2")
                .action("CREATE_ORDER")
                .details("Order for car ID 123")
                .timestamp(LocalDateTime.now().plusMinutes(10))
                .build();

        logRepository.save(log1);
        logRepository.save(log2);

        final List<Log> logs = logRepository.findAll();

        assertAll(
                () -> assertEquals(2, logs.size()),
                () -> assertTrue(logs.contains(log1)),
                () -> assertTrue(logs.contains(log2))
        );
    }

    @Test
    void findAll_withPagination_shouldReturnPaginatedLogs() {
        // Add test logs
        for (int i = 1; i <= 15; i++) {
            final Log log = Log.builder()
                    .user("user" + i)
                    .action("ACTION_" + i)
                    .details("Details " + i)
                    .timestamp(LocalDateTime.now())
                    .build();

            logRepository.save(log);
        }
        final Pagination pagination = new Pagination(2, 5); // Second page, 5 elements per page

        final List<Log> logs = logRepository.findAll(pagination, null);

        assertAll(
                () -> assertEquals(5, logs.size()),
                () -> assertEquals(6L, logs.get(0).getId()),
                () -> assertEquals(10L, logs.get(4).getId())
        );
    }

    @Test
    void findAll_withSorting_shouldReturnSortedLogs() {
        final Log log1 = Log.builder()
                .user("user1")
                .action("LOGIN")
                .details("Successful login")
                .timestamp(LocalDateTime.now())
                .build();
        final Log log2 = Log.builder()
                .user("user2")
                .action("CREATE_ORDER")
                .details("Order for car ID 123")
                .timestamp(LocalDateTime.now().plusMinutes(10))
                .build();
        final Sorting sorting = new Sorting("user", SortOrder.DESC);

        logRepository.save(log1);
        logRepository.save(log2);

        final List<Log> logs = logRepository.findAll(null, sorting);

        assertAll(
                () -> assertEquals("user2", logs.get(0).getUser()),
                () -> assertEquals("user1", logs.get(1).getUser())
        );
    }

    @Test
    void findAll_withPaginationAndSorting_shouldReturnPaginatedAndSortedLogs() {
        for (int i = 1; i <= 15; i++) {
            final Log log = Log.builder()
                    .user("user" + (i % 3 + 1))
                    .action("ACTION_" + i)
                    .details("Details " + i)
                    .timestamp(LocalDateTime.now())
                    .build();

            logRepository.save(log);
        }
        final Pagination pagination = new Pagination(2, 5); // Second page, 5 elements per page
        final Sorting sorting = new Sorting("user", SortOrder.ASC); // Sorting by user in ascending order

        final List<Log> logs = logRepository.findAll(pagination, sorting);

        assertAll(
                () -> assertEquals(5, logs.size()),
                () -> assertEquals("user2", logs.get(0).getUser()),
                () -> assertEquals("user2", logs.get(1).getUser()),
                () -> assertEquals("user2", logs.get(2).getUser()),
                () -> assertEquals("user2", logs.get(3).getUser()),
                () -> assertEquals("user2", logs.get(4).getUser())
        );
    }

    @Test
    void findById_existingLog_shouldReturnLog() {
        final Log log = Log.builder()
                .user("admin")
                .action("UPDATE_CAR")
                .details("Car ID: 5 updated")
                .timestamp(LocalDateTime.now())
                .build();
        final Log savedLog = logRepository.save(log);

        final Optional<Log> foundLog = logRepository.findById(savedLog.getId());

        assertAll(
                () -> assertTrue(foundLog.isPresent()),
                () -> assertEquals(savedLog, foundLog.get())
        );
    }

    @Test
    void findById_nonExistingLog_shouldReturnEmptyOptional() {
        final Optional<Log> foundLog = logRepository.findById(999L);

        assertFalse(foundLog.isPresent());
    }

    @Test
    void save_shouldSaveLogAndGenerateId() {
        final Log log = Log.builder()
                .user("test")
                .action("TEST_ACTION")
                .details("Test details")
                .timestamp(LocalDateTime.now())
                .build();
        final Log savedLog = logRepository.save(log);

        assertAll(
                () -> assertNotNull(savedLog.getId()),
                () -> assertTrue(logRepository.findById(savedLog.getId()).isPresent())
        );
    }

    @Test
    void deleteById_existingLog_shouldDeleteLog() {
        final Log log = Log.builder().user("user").action("DELETE_USER").details("User ID: 10 deleted").timestamp(LocalDateTime.now()).build();
        final Log savedLog = logRepository.save(log);

        logRepository.deleteById(savedLog.getId());

        assertFalse(logRepository.findById(savedLog.getId()).isPresent());
    }
}
