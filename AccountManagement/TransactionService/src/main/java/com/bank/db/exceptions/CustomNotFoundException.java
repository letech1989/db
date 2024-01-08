package com.bank.db.exceptions;

import com.bank.db.enums.ErrorCode;

public class CustomNotFoundException extends RuntimeException {
    private String errorCode;
    public CustomNotFoundException(ErrorCode error) {
        super(error.getMessage());
        this.errorCode = error.getCode();
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
