package com.paypal.bfs.test.employeeserv.api;

import com.paypal.bfs.test.employeeserv.api.model.Address;
import com.paypal.bfs.test.employeeserv.api.model.Employee;
import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ResourceUtils {

    public Employee toEmployee(EmployeeEntity employeeEntity) {
        Employee employee = new Employee();
        if (employeeEntity.getEmployeeId() != null) {
            employee.setId(employeeEntity.getEmployeeId());
        }
        employee.setFirstName(employeeEntity.getFirstName());
        employee.setLastName(employeeEntity.getLastName());
        employee.setDateOfBirth(employeeEntity.getDateOfBirth());

        List<Address> addresses = new ArrayList<>();

        if (Boolean.FALSE.equals(CollectionUtils.isEmpty(employeeEntity.getAddresses()))) {
            employeeEntity.getAddresses().forEach((addressEntity) ->
                    addresses.add(toAddress(addressEntity))
            );
        }
        employee.setAddresses(addresses);
        return employee;
    }

    public List<Employee> toEmployees(List<EmployeeEntity> employeeEntities) {
        if (Boolean.TRUE.equals(CollectionUtils.isEmpty(employeeEntities))) {
            return Collections.EMPTY_LIST;
        }
        List<Employee> employees = new ArrayList<>();
        employeeEntities.forEach((employeeEntity) ->
                employees.add(toEmployee(employeeEntity))
        );
        return employees;
    }

    public Address toAddress(AddressEntity addressEntity) {
        Address address = new Address();
        address.setLine1(addressEntity.getLine1());
        address.setLine2(addressEntity.getLine2());
        address.setCity(addressEntity.getCity());
        address.setState(addressEntity.getState());
        address.setCountry(addressEntity.getCountry());
        address.setZipCode(addressEntity.getZipCode());
        return address;
    }
}
