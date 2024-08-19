package io.ylab.storage.in.memory.storage;

import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.SortOrder;
import io.ylab.storage.in.memory.dto.LogDto;

import java.util.Comparator;

public class InMemoryLogStorage extends InMemoryStorage<LogDto, Long> {

    @Override
    protected Comparator<LogDto> createComparator(Sorting sorting) {
        Comparator<LogDto> comparator = null;
        switch (sorting.getSortBy()) {
            case "id":
                comparator = Comparator.comparing(LogDto::getId);
                break;
            case "user":
                comparator = Comparator.comparing(LogDto::getUser);
                break;
            case "action":
                comparator = Comparator.comparing(LogDto::getAction);
                break;
            case "details":
                comparator = Comparator.comparing(LogDto::getDetails);
                break;
            case "timestamp":
                comparator = Comparator.comparing(LogDto::getTimestamp);
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