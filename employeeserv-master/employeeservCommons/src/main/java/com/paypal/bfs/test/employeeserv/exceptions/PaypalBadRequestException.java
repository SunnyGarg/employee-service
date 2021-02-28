package com.paypal.bfs.test.employeeserv.exceptions;

public class PaypalBadRequestException extends Exception {
    private final String msg;

    public PaypalBadRequestException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
