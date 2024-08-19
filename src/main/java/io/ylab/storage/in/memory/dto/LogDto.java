package io.ylab.storage.in.memory.dto;

import io.ylab.storage.in.memory.data.Identifiable;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class LogDto implements Identifiable<Long> {
    private Long id;
    private String user;
    private String action;
    private String details;
    private LocalDateTime timestamp;
}