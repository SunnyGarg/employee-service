package com.paypal.bfs.test.employeeserv.utils;

import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.entity.IdempotencyEntity;
import com.paypal.bfs.test.employeeserv.repository.IEmployeeRepository;
import com.paypal.bfs.test.employeeserv.repository.IIdempotencyRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class IdempotencyValidator {

    private static final String ENTITY_EMPLOYEE = "Employee";

    private IIdempotencyRepository idempotencyRepository;
    private IEmployeeRepository employeeRepository;

    @Autowired
    public IdempotencyValidator(IIdempotencyRepository idempotencyRepository,
                                IEmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
        this.idempotencyRepository = idempotencyRepository;
    }

    public Optional<EmployeeEntity> checkForIdempotency(String idempotencyToken) {
        Optional<IdempotencyEntity> idempotencyEntity = idempotencyRepository.findByIdempotencyKey(idempotencyToken);
        if (Boolean.FALSE.equals(idempotencyEntity.isPresent())
                || Boolean.TRUE.equals(StringUtils.isEmpty(idempotencyEntity.get().getEntityType()))) {
            return null;
        }
        switch (idempotencyEntity.get().getEntityType()) {
            case ENTITY_EMPLOYEE:
                return employeeRepository.findByEmployeeId(idempotencyEntity.get().getEntityId());
            default:
                return null;
        }
    }
}
