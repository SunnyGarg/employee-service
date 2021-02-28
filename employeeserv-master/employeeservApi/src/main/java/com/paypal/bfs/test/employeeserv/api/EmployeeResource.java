package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.model.PaypalResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Interface for employee resource operations.
 */
public interface EmployeeResource {

    /**
     * Retrieves the {@link Employee} resource by id.
     *
     * @param employeeId
     * @return {@link Employee} resource.
     */
    @RequestMapping(value = "/v1/bfs/employees/{employeeId}", method = RequestMethod.GET)
    ResponseEntity<PaypalResponse> employeeGetById(@PathVariable("employeeId") String employeeId);

    /**
     * Creates the new {@link Employee} Employee.
     *
     * @param employeeEntity
     * @return {@link Employee} resource.
     */
    @RequestMapping(value = "/v1/bfs/employees", method = RequestMethod.POST, produces = "application/json")
    ResponseEntity<PaypalResponse> createEmployee(@RequestHeader HttpHeaders headers, @RequestBody EmployeeEntity employeeEntity);
}
