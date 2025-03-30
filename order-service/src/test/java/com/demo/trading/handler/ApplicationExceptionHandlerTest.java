package com.demo.trading.handler;

import com.demo.trading.common.exception.BusinessException;
import com.demo.trading.common.exception.OperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.MethodParameter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ApplicationExceptionHandlerTest {

    private ApplicationExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new ApplicationExceptionHandler();
    }

    @Test
    void shouldHandleBusinessException() {
        BusinessException ex = new BusinessException("ERR_CODE", "Something went wrong");

        ResponseEntity<Map<String, Object>> response = handler.handleBusinessException(ex);

        assertEquals(400, response.getStatusCodeValue());
        Map<String, Object> body = response.getBody();
        assertNotNull(body);
        List<?> errors = (List<?>) body.get("errors");
        assertEquals(1, errors.size());
    }

    @Test
    void shouldHandleOperationException() {
        OperationException ex = new OperationException("500", "Internal failure");

        ResponseEntity<Map<String, Object>> response = handler.handleOperationException(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals(1, ((List<?>) response.getBody().get("errors")).size());
    }

    @Test
    void shouldHandleMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("orderRequest", "quantity", "Quantity must be greater than 0"),
                new FieldError("orderRequest", "price", "Price cannot be negative")
        ));

        MethodParameter methodParameter = mock(MethodParameter.class);

        MethodArgumentNotValidException exception =
                new MethodArgumentNotValidException(methodParameter, bindingResult);

        ResponseEntity<Map<String, String>> response = handler.handleValidationErrors(exception);
        Map<String, String> errorMap = response.getBody();

        assertEquals(2, errorMap.size());
        assertEquals("Quantity must be greater than 0", errorMap.get("quantity"));
        assertEquals("Price cannot be negative", errorMap.get("price"));
    }



}
