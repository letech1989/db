package com.bank.db.utils;

import com.bank.db.enums.ACTION;

public class AccountInput {


    private String customerNumber;


    private String accountNumber;

    private double amount;

    private ACTION type;

    public AccountInput() {}

    public String getCustomerNumber() {
        return customerNumber;
    }

    public void setCustomerNumber(String customerNumber) {
        this.customerNumber = customerNumber;
    }

    public String getAccountNumber() {
        return accountNumber;
    }
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public ACTION getType() {
        return type;
    }

    public void setType(ACTION type) {
        this.type = type;
    }
}
