package com.paypal.bfs.test.employeeserv.exceptions;

public class PaypalHibernateDAOException extends Exception {
    private final String msg;

    public PaypalHibernateDAOException(String msg) {
        super(msg);
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
