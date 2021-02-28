package com.paypal.bfs.test.employeeserv.exceptions;

public class PaypalEntityNotFoundException extends Exception {
    private final String msg;

    public PaypalEntityNotFoundException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
