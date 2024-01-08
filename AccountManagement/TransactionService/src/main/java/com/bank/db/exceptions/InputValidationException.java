package com.bank.db.exceptions;

import com.bank.db.enums.ErrorCode;

public class InputValidationException  extends RuntimeException{
    private String errorCode;

    public InputValidationException(ErrorCode error) {
        super(error.getMessage());
        this.errorCode = error.getCode();
    }

    public InputValidationException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}
