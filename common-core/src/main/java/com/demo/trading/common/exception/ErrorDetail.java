package com.demo.trading.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ErrorDetail {
    private String errorMessage;
    private String errorCode;
    private LocalDateTime timestamp;
}
