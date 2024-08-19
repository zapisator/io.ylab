package io.ylab.storage.in.memory.storage;

import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.SortOrder;
import io.ylab.storage.in.memory.dto.CarDto;

import java.util.Comparator;

public class InMemoryCarStorage extends InMemoryStorage<CarDto, Long> {

    @Override
    protected Comparator<CarDto> createComparator(Sorting sorting) {
        Comparator<CarDto> comparator = null;
        switch (sorting.getSortBy()) {
            case "id":
                comparator = Comparator.comparing(CarDto::getId);
                break;
            case "brand":
                comparator = Comparator.comparing(CarDto::getBrand);
                break;
            case "model":
                comparator = Comparator.comparing(CarDto::getModel);
                break;
            case "year":
                comparator = Comparator.comparingInt(CarDto::getYear);
                break;
            case "price":
                comparator = Comparator.comparingDouble(CarDto::getPrice);
                break;
            case "status":
                comparator = Comparator.comparing(CarDto::getStatus);
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
