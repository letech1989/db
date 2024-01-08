package com.bank.db.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputValidator {

    public static final String CUSTOMER_PATTERN_STRING = "^[0-9]{15}$";
    public static final String ACCOUNT_NUMBER_PATTERN_STRING = "[0-9]{8}";
    private static final String EMAIL_REGEX ="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String MOBILE_PATTERN = "[0-9]{8}";

    public  boolean isValidString(String data, String regex) {
        if (data == null) {
            return false;
        }
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(data);
        return matcher.matches();
    }


    public  boolean isSearchCriteriaValid(AccountInput accountInput)  {
      return isValidString(accountInput.getAccountNumber(), ACCOUNT_NUMBER_PATTERN_STRING) && isValidString(accountInput.getCustomerNumber(), CUSTOMER_PATTERN_STRING) && accountInput.getAmount() >=0;
    }

    public  boolean isAccountNoValid(String accountNo) {
        return isValidString(accountNo, ACCOUNT_NUMBER_PATTERN_STRING);
    }

    public  boolean isCustomerNoValid(String accountNo) {
        return isValidString(accountNo, CUSTOMER_PATTERN_STRING);
    }

    public  boolean isCreateAccountCriteriaValid(CreateAccountInput createAccountInput)   {
        return (isValidString(createAccountInput.getEmail(), EMAIL_REGEX) && isValidString(createAccountInput.getMobileNumber(),MOBILE_PATTERN));
    }


}
