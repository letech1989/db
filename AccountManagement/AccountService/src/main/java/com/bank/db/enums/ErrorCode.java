package com.bank.db.enums;

public enum ErrorCode {

    INVALID_INPUT("E00001", "Invalid input"),
    NOT_FOUND("E00002", "NO data found"),
    NVALID_TRANSACTION_AMOUNT("E00003","Invalid amount"),
    INVALID_ACCOUNT_NUMBER("E00004","Invalid account number"),
    INVALID_CUSTOMER_ID("E00005","Invalid customer number"),
    INSUFFICIENT_FUNDS("B00001","Insufficient Funds"),
    ACCOUNT_NOT_FOUND("B0002", "Account not found."),
    ACCOUNT_CRETION_FAILED("B0003","Account creation failed. try after sometime"),
    INVALID_CUSTOMER_NAME("E00006","Invalid customer name"),
    INVALID_MOBILE_NUMBER("E00007", "Invalid mobile number"),
    INVALID_EMAIL("E00008", "Invalid email id");

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
