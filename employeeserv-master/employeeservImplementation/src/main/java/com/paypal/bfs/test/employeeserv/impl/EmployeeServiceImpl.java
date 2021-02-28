package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeService;
import com.paypal.bfs.test.employeeserv.api.ResourceUtils;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.entity.IdempotencyEntity;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalBadRequestException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalEntityNotFoundException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalHibernateDAOException;
import com.paypal.bfs.test.employeeserv.repository.IEmployeeRepository;
import com.paypal.bfs.test.employeeserv.repository.IIdempotencyRepository;
import com.paypal.bfs.test.employeeserv.utils.EntityValidator;
import com.paypal.bfs.test.employeeserv.utils.IdempotencyValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Service layer for employee resource.
 */
@Slf4j
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private String IDEMPOTENCY_KEY = "idempotency_key";
    private static final String ENTITY_EMPLOYEE = "Employee";

    private IEmployeeRepository employeeRepository;
    private IdempotencyValidator idempotencyValidator;
    private IIdempotencyRepository idempotencyRepository;

    private ResourceUtils resourceUtils;
    private EntityValidator entityValidator;

    /**
     * Constructor based dependency injection.
     *
     * @param employeeRepository
     * @param resourceUtils
     */
    @Autowired
    public EmployeeServiceImpl(IEmployeeRepository employeeRepository
            , ResourceUtils resourceUtils
            , EntityValidator entityValidator
            , IdempotencyValidator idempotencyValidator
            , IIdempotencyRepository idempotencyRepository) {
        this.employeeRepository = employeeRepository;
        this.resourceUtils = resourceUtils;
        this.entityValidator = entityValidator;
        this.idempotencyValidator = idempotencyValidator;
        this.idempotencyRepository = idempotencyRepository;
    }

    private String getIdempotencyKey(HttpHeaders httpHeaders) {
        if (httpHeaders != null && httpHeaders.containsKey(IDEMPOTENCY_KEY)) {
            return httpHeaders.get(IDEMPOTENCY_KEY).get(0);
        }
        return null;
    }

    /**
     * Method to create an employee
     *
     * @param headers
     * @param employeeEntity
     * @return
     * @throws PaypalHibernateDAOException
     * @throws PaypalBadRequestException
     */
    @Override
    public Employee createEmployee(HttpHeaders headers, EmployeeEntity employeeEntity) throws PaypalHibernateDAOException, PaypalBadRequestException {
        //Validating employee entity.
        entityValidator.validateEmployeeEntity(employeeEntity);

        if (employeeEntity.getEmployeeId() != null
                && employeeRepository.existsById(employeeEntity.getEmployeeId())) {
            throw new PaypalBadRequestException("Employee with id " + employeeEntity.getEmployeeId() + " already exists.");
        }

        //Checking for Idempotency.
        String idempotencyKey = getIdempotencyKey(headers);
        if (Boolean.FALSE.equals(StringUtils.isEmpty(idempotencyKey))) {
            Optional<EmployeeEntity> alreadyExistingEntity = idempotencyValidator.checkForIdempotency(idempotencyKey);
            if (alreadyExistingEntity != null && alreadyExistingEntity.isPresent()) {
                log.debug("Returning already existing entity.");
                return resourceUtils.toEmployee(alreadyExistingEntity.get());
            }
        }

        //Setting UUID for employee id.
        employeeEntity.setEmployeeId(UUID.randomUUID().toString() + System.currentTimeMillis());

        //Saving idempotency token in database.
        IdempotencyEntity idempotencyEntity = new IdempotencyEntity();
        idempotencyEntity.setEntityId(employeeEntity.getEmployeeId());
        idempotencyEntity.setIdempotencyKey(idempotencyKey);
        idempotencyEntity.setEntityType(ENTITY_EMPLOYEE);
        idempotencyEntity.setTimeCreated(new Date());
        idempotencyRepository.save(idempotencyEntity);

        if (Boolean.FALSE.equals(CollectionUtils.isEmpty(employeeEntity.getAddresses()))) {
            employeeEntity.getAddresses().forEach(addressEntity -> {
                addressEntity.setAddressId(UUID.randomUUID().toString() + System.currentTimeMillis());
            });
        }

        EmployeeEntity createdEmployeeEntity = employeeRepository.save(employeeEntity);
        if (createdEmployeeEntity == null) {
            log.error("Failed to create employee with payload {}", employeeEntity);
            throw new PaypalHibernateDAOException("Failed to create employee with payload " + employeeEntity);
        }
        log.debug("Employee with payload {} created successfully.", employeeEntity);
        return resourceUtils.toEmployee(createdEmployeeEntity);
    }

    /**
     * Method to get an employee by id
     *
     * @param employeeId
     * @return
     * @throws PaypalEntityNotFoundException
     */
    @Override
    public Employee getByEmployeeId(String employeeId) throws PaypalEntityNotFoundException {
        Optional<EmployeeEntity> employeeEntity = employeeRepository.findByEmployeeId(employeeId);
        if (Boolean.FALSE.equals(employeeEntity.isPresent())) {
            log.error("Employee with Id {} does not exist.", employeeId);
            throw new PaypalEntityNotFoundException("Employee with Id " + employeeId + " does not exist.");
        }
        log.debug("Employee with Id {} retrieved successfully.", employeeId);
        return resourceUtils.toEmployee(employeeEntity.get());
    }
}
