package io.ylab.storage.in.memory.dto;

import io.ylab.backend.domain.model.CarStatus;
import io.ylab.storage.in.memory.data.Identifiable;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CarDto implements Identifiable<Long> {
    private Long id;
    private String brand;
    private String model;
    private int year;
    private double price;
    private CarStatus status;
}