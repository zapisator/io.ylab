package io.ylab.backend.domain.service;

import io.ylab.backend.domain.model.User;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.backend.infrastructure.repository.UserRepository;
import io.ylab.common.request.filter.ComparisonOperator;
import io.ylab.common.request.filter.FilterCriterion;
import org.junit.jupiter.api.Test;

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

class UserServiceTest {

    @Test
    void findAll_withFiltering_shouldReturnFilteredUsers() {
        final UserRepository userRepository = mock(UserRepository.class);
        final UserService userService = new UserService(userRepository);
        final List<FilterCriterion<?>> criteria = Arrays.asList(
                new FilterCriterion<>("firstName", ComparisonOperator.EQUALS, "John"),
                new FilterCriterion<>("lastName", ComparisonOperator.EQUALS, "Doe")
        );
        when(userRepository.findAll(any(), any()))
                .thenReturn(Arrays.asList(
                                User.builder()
                                        .id(1L)
                                        .firstName("John")
                                        .lastName("Doe")
                                        .email("john.doe@example.com")
                                        .phoneNumber("123-456-7890")
                                        .address("123 Main St")
                                        .build(),
                                User.builder()
                                        .id(2L)
                                        .firstName("Jane")
                                        .lastName("Smith")
                                        .email("jane.smith@example.com")
                                        .phoneNumber("987-654-3210")
                                        .address("456 Oak Ave")
                                        .build(),
                                User.builder()
                                        .id(3L)
                                        .firstName("Peter")
                                        .lastName("Jones")
                                        .email("peter.jones@example.com")
                                        .phoneNumber("555-555-5555")
                                        .address("789 Pine St")
                                        .build()
                        )
                );

        final List<User> filteredUsers = userService.findAll(null, null, criteria);

        assertAll(
                () -> assertEquals(1, filteredUsers.size()),
                () -> assertEquals("John", filteredUsers.get(0).getFirstName()),
                () -> assertEquals("Doe", filteredUsers.get(0).getLastName())
        );
    }

    @Test
    void findAll_withPagination_shouldReturnPaginatedUsers() {
        final UserRepository userRepository = mock(UserRepository.class);
        final UserService userService = new UserService(userRepository);
        when(userRepository.findAll(nullable(Pagination.class), nullable(Sorting.class)))
                .thenReturn(Arrays.asList(
                                User.builder()
                                        .id(1L)
                                        .firstName("John")
                                        .lastName("Doe")
                                        .email("john.doe@example.com")
                                        .phoneNumber("123-456-7890")
                                        .address("123 Main St")
                                        .build(),
                                User.builder()
                                        .id(2L)
                                        .firstName("Jane")
                                        .lastName("Smith")
                                        .email("jane.smith@example.com")
                                        .phoneNumber("987-654-3210")
                                        .address("456 Oak Ave")
                                        .build()
                        )
                );
        final List<User> paginatedCars = userService.findAll(null, null, Collections.emptyList());

        assertEquals(2, paginatedCars.size());
    }

    @Test
    void findAll_withSorting_shouldReturnSortedUsers() {
        final UserRepository userRepository = mock(UserRepository.class);
        final UserService userService = new UserService(userRepository);
        when(userRepository.findAll(nullable(Pagination.class), nullable(Sorting.class)))
                .thenReturn(Arrays.asList(
                        User.builder()
                                .id(2L)
                                .firstName("Jane")
                                .lastName("Smith")
                                .email("jane.smith@example.com")
                                .phoneNumber("987-654-3210")
                                .address("456 Oak Ave")
                                .build(),
                        User.builder()
                                .id(1L)
                                .firstName("John")
                                .lastName("Doe")
                                .email("john.doe@example.com")
                                .phoneNumber("123-456-7890")
                                .address("123 Main St")
                                .build()
                ));

        final List<User> sortedCars = userService.findAll(null, null, Collections.emptyList());

        assertAll(
                () -> assertEquals(2, sortedCars.size()),
                () -> assertEquals("Jane", sortedCars.get(0).getFirstName()),
                () -> assertEquals("John", sortedCars.get(1).getFirstName())
        );
    }

    @Test
    void update_existingUser_shouldUpdateUser() {
        final UserRepository userRepository = mock(UserRepository.class);
        final UserService userService = new UserService(userRepository);
        final User existingUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123-456-7890")
                .address("123 Main St")
                .build();
        final User updatedUser = User.builder()
                .id(1L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phoneNumber("987-654-3210")
                .address("456 Oak Ave")
                .build();
        when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(updatedUser)).thenReturn(updatedUser);

        final User result = userService.update(updatedUser);

        assertEquals(updatedUser, result);
        verify(userRepository).save(updatedUser);
    }

    @Test
    void update_nonExistingUser_shouldThrowException() {
        final UserRepository userRepository = mock(UserRepository.class);
        final User nonExistingUser = User.builder()
                .id(999L)
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .phoneNumber("000-000-0000")
                .address("Test address")
                .build();
        final UserService userService = new UserService(userRepository);
        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.update(nonExistingUser));
        verify(userRepository, never()).save(any());
    }

    @Test
    void create_shouldSaveUserAndLogAction() {
        final User user = User.builder()
                .firstName("Test")
                .lastName("Test")
                .email("test@test.com")
                .phoneNumber("000-000-0000")
                .address("Test address")
                .build();
        final UserRepository userRepository = mock(UserRepository.class);
        final UserService userService = new UserService(userRepository);
        when(userRepository.save(any(User.class))).thenReturn(user);

        final User createdCar = userService.create(user);

        assertEquals(user, createdCar);
        verify(userRepository).save(user);
    }

    @Test
    void deleteById_shouldDeleteUserAndLogAction() {
        final UserRepository userRepository = mock(UserRepository.class);
        final UserService userService = new UserService(userRepository);
        final Long userId = 1L;

        userService.deleteById(userId);

        verify(userRepository).deleteById(userId);
    }

}