package com.demo.trading.handler;

import com.demo.trading.common.exception.BusinessException;
import com.demo.trading.common.exception.ErrorDetail;
import com.demo.trading.common.exception.OperationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class ApplicationExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Map<String, Object>> handleBusinessException(BusinessException ex) {
        ErrorDetail detail = new ErrorDetail(
                ex.getErrorMessage(),
                ex.getErrorCode(),
                LocalDateTime.now()
        );

        Map<String, Object> errorBody = new HashMap<>();
        errorBody.put("errors", Collections.singletonList(detail));

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(OperationException.class)
    public ResponseEntity<Map<String, Object>> handleOperationException(OperationException ex) {
        ErrorDetail error = new ErrorDetail(
                ex.getErrorMessage(),
                ex.getErrorCode(),
                LocalDateTime.now()
        );

        Map<String, Object> response = new HashMap<>();
        response.put("errors", Collections.singletonList(error));

        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
