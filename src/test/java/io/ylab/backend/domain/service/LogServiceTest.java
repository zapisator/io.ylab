package io.ylab.backend.domain.service;

import io.ylab.backend.domain.model.Log;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.backend.infrastructure.repository.LogRepository;
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

class LogServiceTest {

    @Test
    void findAll_withFiltering_shouldReturnFilteredLogs() {
        final LogRepository logRepository = mock(LogRepository.class);
        final LogService logService = new LogService(logRepository);
        final List<FilterCriterion<?>> criteria = Arrays.asList(
                new FilterCriterion<>("user", ComparisonOperator.EQUALS, "admin"),
                new FilterCriterion<>("action", ComparisonOperator.EQUALS, "CREATE_CAR")
        );
        when(logRepository.findAll(any(), any()))
                .thenReturn(Arrays.asList(
                                Log.builder()
                                        .id(1L)
                                        .user("admin")
                                        .action("CREATE_CAR")
                                        .details("Car created successfully")
                                        .timestamp(LocalDateTime.now())
                                        .build(),
                                Log.builder()
                                        .id(2L)
                                        .user("user")
                                        .action("UPDATE_CAR")
                                        .details("Car updated")
                                        .timestamp(LocalDateTime.now().plusHours(1))
                                        .build(),
                                Log.builder()
                                        .id(3L)
                                        .user("admin")
                                        .action("DELETE_CAR")
                                        .details("Car deleted: ID 123")
                                        .timestamp(LocalDateTime.now().plusHours(2))
                                        .build()
                        )
                );

        final List<Log> filteredLogs = logService.findAll(null, null, criteria);

        assertAll(
                () -> assertEquals(1, filteredLogs.size()),
                () -> assertEquals("admin", filteredLogs.get(0).getUser()),
                () -> assertEquals("CREATE_CAR", filteredLogs.get(0).getAction())
        );
    }

    @Test
    void findAll_withPagination_shouldReturnPaginatedLogs() {
        final LogRepository logRepository = mock(LogRepository.class);
        final LogService logService = new LogService(logRepository);
        when(logRepository.findAll(nullable(Pagination.class), nullable(Sorting.class)))
                .thenReturn(Arrays.asList(
                        Log.builder()
                                .id(1L)
                                .user("admin")
                                .action("CREATE_CAR")
                                .details("Car created successfully")
                                .timestamp(LocalDateTime.now())
                                .build(),
                        Log.builder()
                                .id(2L)
                                .user("user")
                                .action("UPDATE_CAR")
                                .details("Car updated")
                                .timestamp(LocalDateTime.now().plusHours(1))
                                .build()
                ));
        final List<Log> paginatedCars = logService.findAll(null, null, Collections.emptyList());

        assertEquals(2, paginatedCars.size());
    }

    @Test
    void findAll_withSorting_shouldReturnSortedLogs() {
        final LogRepository logRepository = mock(LogRepository.class);
        final LogService logService = new LogService(logRepository);
        when(logRepository.findAll(nullable(Pagination.class), nullable(Sorting.class)))
                .thenReturn(Arrays.asList(
                        Log.builder()
                                .id(2L)
                                .user("user")
                                .action("UPDATE_CAR")
                                .details("Car updated")
                                .timestamp(LocalDateTime.now().plusHours(1))
                                .build(),
                        Log.builder()
                                .id(1L)
                                .user("admin")
                                .action("CREATE_CAR")
                                .details("Car created successfully")
                                .timestamp(LocalDateTime.now())
                                .build()
                ));
        final List<Log> sortedCars = logService.findAll(null, null, Collections.emptyList());

        assertAll(
                () -> assertEquals(2, sortedCars.size()),
                () -> assertEquals("user", sortedCars.get(0).getUser()),
        () -> assertEquals("admin", sortedCars.get(1).getUser())
        );
    }

    @Test
    void update_existingLog_shouldUpdateLog() {
        final LogRepository logRepository = mock(LogRepository.class);
        final LogService logService = new LogService(logRepository);
        final Log existingLog = Log.builder()
                .id(1L)
                .user("admin")
                .action("CREATE_CAR")
                .details("Car created successfully")
                .timestamp(LocalDateTime.now())
                .build();
        final Log updatedLog = Log.builder()
                .id(1L)
                .user("user")
                .action("UPDATE_CAR")
                .details("Car updated successfully")
                .timestamp(LocalDateTime.now().plusHours(1))
                .build();
        when(logRepository.findById(1L)).thenReturn(Optional.of(existingLog));
        when(logRepository.save(updatedLog)).thenReturn(updatedLog);

        final Log result = logService.update(updatedLog);

        assertEquals(updatedLog, result);
        verify(logRepository).save(updatedLog);
    }

    @Test
    void update_nonExistingLog_shouldThrowException() {
        final LogRepository logRepository = mock(LogRepository.class);
        final Log nonExistingLog = Log.builder()
                .id(999L)
                .user("Test")
                .action("TEST_ACTION")
                .details("Test details")
                .timestamp(LocalDateTime.now())
                .build();
        final LogService logService = new LogService(logRepository);
        when(logRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> logService.update(nonExistingLog));
        verify(logRepository, never()).save(any());
    }

    @Test
    void create_shouldSaveLogAndLogAction() {
        final Log log = Log.builder()
                .user("user")
                .action("CREATE_LOG")
                .details("Test details")
                .timestamp(LocalDateTime.now())
                .build();
        final LogRepository logRepository = mock(LogRepository.class);
        final LogService logService = new LogService(logRepository);
        when(logRepository.save(any(Log.class))).thenReturn(log);

        final Log createdLog = logService.create(log);

        assertEquals(log, createdLog);
        verify(logRepository).save(log);
    }

    @Test
    void deleteById_shouldDeleteLogAndLogAction() {
        final LogRepository logRepository = mock(LogRepository.class);
        final LogService logService = new LogService(logRepository);
        final Long logId = 1L;

        logService.deleteById(logId);

        verify(logRepository).deleteById(logId);
    }

}