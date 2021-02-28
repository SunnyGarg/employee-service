package com.paypal.bfs.test.employeeserv.repository;

import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository class for Employee Entity.
 */
@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeEntity, String> {
    public Optional<EmployeeEntity> findByEmployeeId(String employeeId);
}
