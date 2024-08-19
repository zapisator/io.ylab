package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.User;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.storage.in.memory.dto.UserDto;
import io.ylab.storage.in.memory.management.StorageManager;
import io.ylab.storage.in.memory.storage.Storage;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class InMemoryUserRepository implements UserRepository {
    private final StorageManager storageManager;

    public InMemoryUserRepository(StorageManager storageManager) {
        this.storageManager = storageManager;
    }

    private Storage<UserDto, Long> userStorage() {
        return storageManager.getStorage("User");
    }

    @Override
    public List<User> findAll() {
        return userStorage().findAll().stream()
                .map(this::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public List<User> findAll(Pagination pagination, Sorting sorting) {
        return userStorage().findAll(sorting, pagination).stream()
                .map(this::toUser)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userStorage().findById(id)
                .map(this::toUser);
    }

    @Override
    public User save(User user) {
        final UserDto userDto = toUserDto(user);
        userStorage().save(userDto);
        return toUser(userDto);
    }

    @Override
    public void deleteById(Long id) {
        userStorage().deleteById(id);
    }

    private UserDto toUserDto(final User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .build();
    }

    private User toUser(final UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .email(userDto.getEmail())
                .phoneNumber(userDto.getPhoneNumber())
                .address(userDto.getAddress())
                .build();
    }
}