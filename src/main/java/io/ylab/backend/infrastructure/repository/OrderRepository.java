package io.ylab.backend.infrastructure.repository;

import io.ylab.backend.domain.model.Order;
import io.ylab.backend.infrastructure.repository.common.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
