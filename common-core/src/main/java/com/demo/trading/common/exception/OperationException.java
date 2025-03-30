package com.demo.trading.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class OperationException extends RuntimeException {

    private final String errorCode;
    private final String errorMessage;
    private final HttpStatus httpStatus;

    public OperationException(String errorCode, String errorMessage) {
        this(errorCode, errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    public OperationException(String errorCode, String errorMessage, HttpStatus httpStatus) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.httpStatus = httpStatus;
    }
}
