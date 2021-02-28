package com.paypal.bfs.test.employeeserv.impl;

import com.paypal.bfs.test.employeeserv.api.EmployeeResource;
import com.paypal.bfs.test.employeeserv.api.EmployeeService;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalBadRequestException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalEntityNotFoundException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalHibernateDAOException;
import com.paypal.bfs.test.employeeserv.model.PaypalResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

/**
 * Implementation class for employee resource.
 */
@Slf4j
@RestController
public class EmployeeResourceImpl implements EmployeeResource {

    private EmployeeService employeeService;

    /**
     * Constructor based dependency injection.
     *
     * @param employeeService
     */
    @Autowired
    public EmployeeResourceImpl(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Method to get employee by an id.
     *
     * @param employeeId employee id.
     * @return PaypalResponse
     */
    @Override
    public ResponseEntity<PaypalResponse> employeeGetById(String employeeId) {
        Employee employee;
        try {
            employee = employeeService.getByEmployeeId(employeeId);
        } catch (PaypalEntityNotFoundException e) {
            return new ResponseEntity<>(new PaypalResponse(e.getMessage(), null), HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(new PaypalResponse(null, employee), HttpStatus.OK);
    }

    /**
     * Method to create an employee.
     *
     * @param employeeEntity
     * @return PaypalResponse
     */
    @Override
    public ResponseEntity<PaypalResponse> createEmployee(HttpHeaders headers, EmployeeEntity employeeEntity) {
        Employee employee;
        try {
            employee = employeeService.createEmployee(headers, employeeEntity);
        } catch (PaypalHibernateDAOException | PaypalBadRequestException e) {
            return new ResponseEntity<>(new PaypalResponse(e.getMessage(), null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new PaypalResponse(null
                , employee), HttpStatus.CREATED);
    }
}
