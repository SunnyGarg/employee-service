package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalBadRequestException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalEntityNotFoundException;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalHibernateDAOException;
import org.springframework.http.HttpHeaders;

/**
 * Interface for Employee Service layer
 */
public interface EmployeeService {

    /**
     * Interface method for create an employee.
     *
     * @param employeeEntity
     * @return
     * @throws PaypalHibernateDAOException
     */
    Employee createEmployee(HttpHeaders headers, EmployeeEntity employeeEntity) throws PaypalHibernateDAOException, PaypalBadRequestException;

    /**
     * Interface method to get an employee by id
     *
     * @param employeeId
     * @return
     * @throws PaypalEntityNotFoundException
     */
    Employee getByEmployeeId(String employeeId) throws PaypalEntityNotFoundException;
}
