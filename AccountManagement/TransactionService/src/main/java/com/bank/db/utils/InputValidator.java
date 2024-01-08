package com.bank.db.utils;

import com.bank.db.exceptions.InputValidationException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    public static final String CUSTOMER_PATTERN_STRING = "^[0-9]{15}";
    private static final String CUSTOMER_NAME_PATTERN = "^[a-zA-Z]+(?:[\\s.'-][a-zA-Z]+)*$";
    public static final String ACCOUNT_NUMBER_PATTERN_STRING = "[0-9]{8}";
    private static final String EMAIL_REGEX ="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String MOBILE_PATTERN = "[0-9]{8}";

    public  boolean isValidString(String data, String regex) {
        if (data == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(data);
        return !(matcher.matches());
    }

    public  boolean isSearchCriteriaValid(AccountInput accountInput)  {

        List<String> errorList = new ArrayList<>();
        if(accountInput.getAccountNumber() ==null || accountInput.getAccountNumber().isEmpty() || isValidString(accountInput.getAccountNumber(), ACCOUNT_NUMBER_PATTERN_STRING)){
            errorList.add("account number is null or empty or not valid");
            System.out.println("shamsher1:"+accountInput.getType());
        }
        if(accountInput.getCustomerNumber() ==null || accountInput.getCustomerNumber().isEmpty() || isValidString(accountInput.getCustomerNumber(), CUSTOMER_PATTERN_STRING)){
            errorList.add("customer number is null or empty or not valid");
            System.out.println("shamsher 2:"+accountInput.getType());
        }
        if(accountInput.getAmount() <= 0 ){
            errorList.add(" Amount is negative or null or 0");
        }

        if (!errorList.isEmpty()) {
            throw new InputValidationException("E0000", "Invalid input : "+errorList.toString());
        }

        return true;
    }

    public  boolean isAccountNoValid(String accountNo) {
        return !isValidString(accountNo, ACCOUNT_NUMBER_PATTERN_STRING);
    }

    public  boolean isCustomerNoValid(String accountNo) {
        return !isValidString(accountNo, CUSTOMER_PATTERN_STRING);
    }
}
