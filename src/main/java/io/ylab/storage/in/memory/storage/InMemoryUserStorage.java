package io.ylab.storage.in.memory.storage;

import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.SortOrder;
import io.ylab.storage.in.memory.dto.UserDto;

import java.util.Comparator;

public class InMemoryUserStorage extends InMemoryStorage<UserDto, Long> {

    @Override
    protected Comparator<UserDto> createComparator(Sorting sorting) {
        Comparator<UserDto> comparator = null;
        switch (sorting.getSortBy()) {
            case "id":
                comparator = Comparator.comparing(UserDto::getId);
                break;
            case "firstName":
                comparator = Comparator.comparing(UserDto::getFirstName);
                break;
            case "lastName":
                comparator = Comparator.comparing(UserDto::getLastName);
                break;
            case "email":
                comparator = Comparator.comparing(UserDto::getEmail);
                break;
            case "phoneNumber":
                comparator = Comparator.comparing(UserDto::getPhoneNumber);
                break;
            case "address":
                comparator = Comparator.comparing(UserDto::getAddress);
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