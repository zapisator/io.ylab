package io.ylab.storage.in.memory.storage;

import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.SortOrder;
import io.ylab.storage.in.memory.dto.OrderDto;

import java.util.Comparator;

public class InMemoryOrderStorage extends InMemoryStorage<OrderDto, Long> {

    @Override
    protected Comparator<OrderDto> createComparator(Sorting sorting) {
        Comparator<OrderDto> comparator = null;
        switch (sorting.getSortBy()) {
            case "id":
                comparator = Comparator.comparing(OrderDto::getId);
                break;
            case "userId":
                comparator = Comparator.comparing(OrderDto::getUserId);
                break;
            case "carId":
                comparator = Comparator.comparing(OrderDto::getCarId);
                break;
            case "quantity":
                comparator = Comparator.comparingInt(OrderDto::getQuantity);
                break;
            case "status":
                comparator = Comparator.comparing(OrderDto::getStatus);
                break;
            case "type":
                comparator = Comparator.comparing(OrderDto::getType);
                break;
            case "createdAt":
                comparator = Comparator.comparing(OrderDto::getCreatedAt);
                break;
            case "updatedAt":
                comparator = Comparator.comparing(OrderDto::getUpdatedAt);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + sorting.getSortBy());
        }

        if (sorting.getSortOrder() == SortOrder.DESC) {
            comparator = comparator.reversed();
        }
        return comparator;
    }
}