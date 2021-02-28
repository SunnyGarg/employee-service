package com.paypal.bfs.test.employeeserv.repository;

import com.paypal.bfs.test.employeeserv.entity.IdempotencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository class for Idempotent Entity.
 */
@Repository
public interface IIdempotencyRepository extends JpaRepository<IdempotencyEntity, String> {
    public Optional<IdempotencyEntity> findByIdempotencyKey(String idempotencyToken);
}
