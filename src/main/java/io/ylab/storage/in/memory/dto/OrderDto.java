package io.ylab.storage.in.memory.dto;

import io.ylab.backend.domain.model.OrderStatus;
import io.ylab.backend.domain.model.OrderType;
import io.ylab.storage.in.memory.data.Identifiable;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class OrderDto implements Identifiable<Long> {
    private Long id;
    private Long userId;
    private Long carId;
    private int quantity;
    private OrderStatus status;
    private OrderType type;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
