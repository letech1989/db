package com.bank.db.utils;

import com.mifmif.common.regex.Generex;

import static com.bank.db.utils.constants.ACCOUNT_NUMBER_PATTERN_STRING;
import static com.bank.db.utils.constants.CUSTOMER_PATTERN_STRING;

public class CodeGenerator {
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
