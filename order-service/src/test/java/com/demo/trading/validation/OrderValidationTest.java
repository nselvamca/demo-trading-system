package com.demo.trading.validation;

import com.demo.trading.common.exception.BusinessException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.demo.trading.util.TestConstant.*;
import static org.junit.jupiter.api.Assertions.*;

class OrderValidationTest {

    private OrderValidation orderValidation;

    @BeforeEach
    void setUp() {
        orderValidation = new OrderValidation();
    }

    @Test
    void shouldThrowHighQuantityException_ForNonBSE_WithQtyAbove2000() {

        BusinessException ex = assertThrows(BusinessException.class, () -> orderValidation.validateRequest(FAILURE_HIGHER_QUANTITY));
        assertEquals("HIGHER_QUANTITY", ex.getErrorCode());
        assertEquals("Quantity is higher", ex.getMessage());
    }

    @Test
    void shouldThrowHighQuantityExchangeException_ForBSE_WithQtyAbove1500() {

        BusinessException ex = assertThrows(BusinessException.class, () ->
                orderValidation.validateRequest(FAILURE_HIGHER_QUANTITY_BSE));
        assertEquals("HIGHER_QUANTITY_EXCHANGE", ex.getErrorCode());
        assertEquals("Quantity is higher, for the exchange BSE", ex.getMessage());
    }

    @Test
    void shouldPassValidation_ForBSE_WithQtyBelow1500() {

        assertDoesNotThrow(() -> orderValidation.validateRequest(SUCCESS_ORDER_BSE));
    }

    @Test
    void shouldThrowHighQuantityExchangeException_IfRequestNull() {
        BusinessException ex = assertThrows(BusinessException.class, () ->
                orderValidation.validateRequest(null));
        assertEquals("ORDER_REQUEST_NULL", ex.getErrorCode());
        assertEquals("Order request cannot be null", ex.getMessage());
    }


}

