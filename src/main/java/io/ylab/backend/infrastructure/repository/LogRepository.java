package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Log;
import io.ylab.backend.infrastructure.repository.common.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
