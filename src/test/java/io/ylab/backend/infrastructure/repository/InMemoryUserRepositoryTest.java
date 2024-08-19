package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.User;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.common.request.SortOrder;
import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.management.version.StorageVersion;
import io.ylab.storage.in.memory.management.version.StorageVersionManager;
import io.ylab.storage.in.memory.storage.InMemoryOrderStorage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryUserRepositoryTest {
    private InMemoryUserRepository userRepository;

    public static class TestUserStorageVersion extends TestStorageVersion {

        public TestUserStorageVersion(StorageManager storageManager) {
            super(storageManager);
        }

        @Override
        public void upgrade(StorageManager storageManager) {
            storageManager.addStorage("User", new InMemoryOrderStorage());
        }
    }

    @BeforeEach
    void setUp() {
        final StorageManager storageManager = new StorageManager();
        final StorageVersion version = new TestUserStorageVersion(storageManager);

        new StorageVersionManager(storageManager)
                .addVersion(version)
                .initializeStorage();

        userRepository = new InMemoryUserRepository(storageManager);
    }

    @Test
    void findAll_shouldReturnAllUsers() {
        final User user1 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123-456-7890")
                .address("123 Main St")
                .build();
        final User user2 = User.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phoneNumber("987-654-3210")
                .address("456 Oak Ave")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        final List<User> users = userRepository.findAll();

        assertAll(
                () -> assertEquals(2, users.size()),
                () -> assertTrue(users.contains(user1)),
                () -> assertTrue(users.contains(user2))
        );
    }

    @Test
    void findAll_withPagination_shouldReturnPaginatedUsers() {
        for (int i = 1; i <= 15; i++) {
            final User user = User.builder()
                    .firstName("FirstName" + i)
                    .lastName("LastName" + i)
                    .email("user" + i + "@example.com")
                    .phoneNumber("123-456-789" + i)
                    .address(i + " Some Street")
                    .build();

            userRepository.save(user);
        }
        final Pagination pagination = new Pagination(2, 5);
        final List<User> users = userRepository.findAll(pagination, null);

        assertAll(
                () -> assertEquals(5, users.size()),
                () -> assertEquals(6L, users.get(0).getId()),
                () -> assertEquals(10L, users.get(4).getId())
        );
    }

    @Test
    void findAll_withSorting_shouldReturnSortedUsers() {
        final User user1 = User.builder()
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .phoneNumber("123-456-7890")
                .address("123 Main St")
                .build();
        final User user2 = User.builder()
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .phoneNumber("987-654-3210")
                .address("456 Oak Ave")
                .build();
        final Sorting sorting = new Sorting("lastName", SortOrder.DESC);
        final List<User> users = userRepository.findAll(null, sorting);

        userRepository.save(user1);
        userRepository.save(user2);

        assertAll(
                () -> assertEquals("Smith", users.get(0).getLastName()),
                () -> assertEquals("Doe", users.get(1).getLastName())
        );
    }

    @Test
    void findAll_withPaginationAndSorting_shouldReturnPaginatedAndSortedUsers() {
        for (int i = 1; i <= 15; i++) {
            final User user = User.builder()
                    .firstName("FirstName" + i)
                    .lastName("LastName" + (i % 3 + 1))
                    .email("user" + i + "@example.com")
                    .phoneNumber("123-456-789" + i)
                    .address(i + " Some Street")
                    .build();

            userRepository.save(user);
        }
        final Pagination pagination = new Pagination(2, 5);
        final Sorting sorting = new Sorting("lastName", SortOrder.ASC);

        final List<User> users = userRepository.findAll(pagination, sorting);

        assertAll(
                () -> assertEquals(5, users.size()),
                () -> assertEquals("LastName1", users.get(0).getLastName()),
                () -> assertEquals("LastName1", users.get(1).getLastName()),
                () -> assertEquals("LastName2", users.get(2).getLastName()),
                () -> assertEquals("LastName2", users.get(3).getLastName()),
                () -> assertEquals("LastName3", users.get(4).getLastName())
        );
    }

    @Test
    void findById_existingUser_shouldReturnUser() {
        User user = User.builder().firstName("Alice").lastName("Johnson").email("alice.j@example.com").phoneNumber("555-123-4567").address("789 Elm St").build();
        user = userRepository.save(user);

        Optional<User> foundUser = userRepository.findById(user.getId());

        assertTrue(foundUser.isPresent());
        assertEquals(user, foundUser.get());
    }

    @Test
    void findById_nonExistingUser_shouldReturnEmptyOptional() {
        final Optional<User> foundUser = userRepository.findById(999L);

        assertFalse(foundUser.isPresent());
    }

    @Test
    void save_shouldSaveUserAndGenerateId() {
        final User user = User.builder()
                .firstName("Bob")
                .lastName("Williams")
                .email("bob.w@example.com")
                .phoneNumber("111-222-3333")
                .address("444 Pine Ln")
                .build();
        final User savedUser = userRepository.save(user);

        assertAll(
                () -> assertNotNull(savedUser.getId()),
                () -> assertTrue(userRepository.findById(savedUser.getId()).isPresent())
        );
    }

    @Test
    void deleteById_existingUser_shouldDeleteUser() {
        final User user = User.builder().firstName("Charlie").lastName("Brown").email("charlie.b@example.com").phoneNumber("777-888-9999").address("1010 Birch Rd").build();
        final User savedUser = userRepository.save(user);

        userRepository.deleteById(savedUser.getId());

        assertFalse(userRepository.findById(savedUser.getId()).isPresent());
    }

}