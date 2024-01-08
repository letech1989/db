package com.bank.db.utils;

import com.bank.db.enums.ErrorCode;
import com.bank.db.exceptions.InputValidationException;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InputValidator  {

    public static final String CUSTOMER_PATTERN_STRING = "^[0-9]{15}";
    private static final String CUSTOMER_NAME_PATTERN = "^[a-zA-Z]+(?:[\\s.'-][a-zA-Z]+)*$";
    public static final String ACCOUNT_NUMBER_PATTERN_STRING = "[0-9]{8}";
    private static final String EMAIL_REGEX ="^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String MOBILE_PATTERN = "[0-9]{8}";

    public  static boolean isValidString(String data, String regex) {
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
        }
        if(accountInput.getCustomerNumber() ==null || accountInput.getCustomerNumber().isEmpty() || isValidString(accountInput.getCustomerNumber(), CUSTOMER_PATTERN_STRING)){
            errorList.add("customer number is null or empty or not valid");
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

    public  boolean isCreateAccountCriteriaValid(CreateAccountInput createAccountInput)   {


        List<String> errorList = new ArrayList<>();
        if(createAccountInput.getCustomerName() ==null || createAccountInput.getCustomerName().isEmpty() || isValidString(createAccountInput.getCustomerName(), CUSTOMER_NAME_PATTERN)){
            errorList.add("Customer name cannot be null or empty or should contain only alphabet");
        }
        if(createAccountInput.getAddress() ==null || createAccountInput.getAddress().isEmpty() ){
            errorList.add("Customer address cannot be null or empty ");

        }
        if(createAccountInput.getEmail() ==null || createAccountInput.getEmail().isEmpty() || isValidString(createAccountInput.getEmail(), EMAIL_REGEX)){
            errorList.add("Customer email cannot be null or empty or invalid email");
        }
        if(createAccountInput.getMobileNumber() ==null || createAccountInput.getMobileNumber().isEmpty() || isValidString(createAccountInput.getMobileNumber(), MOBILE_PATTERN)){
            errorList.add("Customer mobile number cannot be null or empty or  should contain 8 digit");
        }
        if (!errorList.isEmpty()) {
            throw new InputValidationException("E0000", "Invalid input : "+errorList.toString());
        }
        return  true;
    }






}
