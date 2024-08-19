package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.User;
import io.ylab.backend.infrastructure.repository.common.JpaRepository;

public interface UserRepository  extends JpaRepository<User, Long> {
}
