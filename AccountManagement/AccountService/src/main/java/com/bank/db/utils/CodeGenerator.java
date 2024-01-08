package com.bank.db.utils;

import com.mifmif.common.regex.Generex;



public class CodeGenerator {
    public static final String CUSTOMER_PATTERN_STRING = "^[0-9]{15}";
    public static final String ACCOUNT_NUMBER_PATTERN_STRING = "[0-9]{8}";
    Generex customerNumber = new Generex(CUSTOMER_PATTERN_STRING);
    Generex accountNumberGenerex = new Generex(ACCOUNT_NUMBER_PATTERN_STRING);

    public CodeGenerator(){}

    public String generateCustomerNumber() {
        return customerNumber.random();
    }

    public String generateAccountNumber() {
        return accountNumberGenerex.random();
    }
}
