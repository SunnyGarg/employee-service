package com.paypal.bfs.test.employeeserv.repository;

import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository class for Address Entity.
 */
@Repository
public interface IAddressRepository extends JpaRepository<AddressEntity, String> {
}
