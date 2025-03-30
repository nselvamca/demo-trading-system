package com.demo.trading.common.exception;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class BusinessException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;

    public BusinessException(String errorCode, String errorMessage) {
        super(errorMessage); // for stack trace and default exception message
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public BusinessException(String errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }
}

