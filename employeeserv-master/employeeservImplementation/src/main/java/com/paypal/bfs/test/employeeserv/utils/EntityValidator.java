package com.paypal.bfs.test.employeeserv.utils;

import com.paypal.bfs.test.employeeserv.entity.AddressEntity;
import com.paypal.bfs.test.employeeserv.entity.EmployeeEntity;
import com.paypal.bfs.test.employeeserv.exceptions.PaypalBadRequestException;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class EntityValidator {

    public void validateEmployeeEntity(EmployeeEntity employeeEntity) throws PaypalBadRequestException {
        if (employeeEntity == null) {
            throw new PaypalBadRequestException("Employee details are required.");
        }

        if (Boolean.TRUE.equals(StringUtils.isEmpty(employeeEntity.getFirstName()))) {
            throw new PaypalBadRequestException("Employee first name is mandatory.");
        }
        if (Boolean.TRUE.equals(StringUtils.isEmpty(employeeEntity.getLastName()))) {
            throw new PaypalBadRequestException("Employee last name is mandatory.");
        }
        if (Boolean.TRUE.equals(StringUtils.isEmpty(employeeEntity.getDateOfBirth()))) {
            throw new PaypalBadRequestException("Employee date of birth is mandatory.");
        }

        if (employeeEntity.getAddresses() == null || employeeEntity.getAddresses().size() == 0) {
            throw new PaypalBadRequestException("Employee address is required.");
        }

        for (AddressEntity addressEntity : employeeEntity.getAddresses()) {
            if (addressEntity == null) {
                throw new PaypalBadRequestException("Address is mandatory.");
            }
            if (Boolean.TRUE.equals(StringUtils.isEmpty(addressEntity.getLine1()))) {
                throw new PaypalBadRequestException("Address line1 is mandatory.");
            }
            if (Boolean.TRUE.equals(StringUtils.isEmpty(addressEntity.getCity()))) {
                throw new PaypalBadRequestException("Address city is mandatory.");
            }
            if (Boolean.TRUE.equals(StringUtils.isEmpty(addressEntity.getState()))) {
                throw new PaypalBadRequestException("Address state is mandatory.");
            }
            if (Boolean.TRUE.equals(StringUtils.isEmpty(addressEntity.getCountry()))) {
                throw new PaypalBadRequestException("Address country is mandatory.");
            }
            if (Boolean.TRUE.equals(StringUtils.isEmpty(addressEntity.getZipCode()))) {
                throw new PaypalBadRequestException("Address zip code is mandatory.");
            }
        }
    }
}