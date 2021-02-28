package com.paypal.bfs.test.employeeserv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


/**
 * Response Object used to return from Resource API.
 */
@Setter
@Getter
@AllArgsConstructor
public class PaypalResponse {
    private String errorMessage;
    private Object payload;
}
