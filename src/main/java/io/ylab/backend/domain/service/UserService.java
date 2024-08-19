package io.ylab.backend.domain.service;

import io.ylab.backend.domain.model.User;
import io.ylab.backend.domain.service.dto.Pagination;
import io.ylab.backend.domain.service.dto.Sorting;
import io.ylab.backend.domain.service.search.ReflectiveSpecification;
import io.ylab.backend.domain.service.search.Specification;
import io.ylab.backend.infrastructure.repository.UserRepository;
import io.ylab.common.request.filter.FilterCriterion;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class UserService implements FindAllWithPaginationSortingAndSpecificationService<User, Long> {
    private final UserRepository userRepository;

    @Override
    public List<User> findAll(
            Pagination pagination, Sorting sorting,
            List<FilterCriterion<?>> criteria
    ) {
        final List<User> users = userRepository.findAll(pagination, sorting);
        final Specification<User> carSpecification = ReflectiveSpecification.of(User.class, criteria);
        return carSpecification.apply(users);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User create(User user) {
        return userRepository.save(user);
    }

    @Override
    public User update(User user) {
        if (!userRepository.findById(user.getId()).isPresent()) {
            throw new IllegalArgumentException("User with ID " + user.getId() + " not found");
        }

        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }
}