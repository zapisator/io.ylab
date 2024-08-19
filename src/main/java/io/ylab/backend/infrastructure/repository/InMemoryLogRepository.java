package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Log;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.storage.in.memory.dto.LogDto;
import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.storage.Storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryLogRepository implements LogRepository{
    private final StorageManager storageManager;

    public InMemoryLogRepository(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    private Storage<LogDto, Long> logStorage() {
        return storageManager.getStorage("Log");
    }

    @Override
    public List<Log> findAll() {
        return logStorage().findAll().stream()
                .map(this::toLog)
                .collect(Collectors.toList());
    }

    @Override
    public List<Log> findAll(Pagination pagination, Sorting sorting) {
        return logStorage().findAll(sorting, pagination).stream()
                .map(this::toLog)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Log> findById(Long id) {
        return logStorage().findById(id)
                .map(this::toLog);
    }

    @Override
    public Log save(Log log) {
        final LogDto logDto = toLogDto(log);
        logStorage().save(logDto);
        return toLog(logDto);
    }

    @Override
    public void deleteById(Long id) {
        logStorage().deleteById(id);
    }

    private LogDto toLogDto(final Log log) {
        return LogDto.builder()
                .id(log.getId())
                .user(log.getUser())
                .action(log.getAction())
                .details(log.getDetails())
                .timestamp(log.getTimestamp())
                .build();
    }

    private Log toLog(final LogDto logDto) {
        return Log.builder()
                .id(logDto.getId())
                .user(logDto.getUser())
                .action(logDto.getAction())
                .details(logDto.getDetails())
                .timestamp(logDto.getTimestamp())
                .build();
    }
}
