package com.demo.trading.mapper;

import com.demo.trading.model.response.OrderResponse;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderResponseMapperTest {

    @Test
    void shouldReturnSuccessResponse() {
        String orderId = "abc123";

        OrderResponse response = OrderResponseMapper.successResponse(orderId);

        assertNotNull(response);
        assertEquals("Success", response.getStatus());
        assertEquals(orderId, response.getOrderId());
        assertNotNull(response.getTimestamp());
        assertNull(response.getErrorMessage());
    }

    @Test
    void shouldReturnFailureResponse() {

        OrderResponse response = OrderResponseMapper.failureResponse();

        assertNotNull(response);
        assertEquals("Failure", response.getStatus());
        assertNull(response.getOrderId());
        assertNotNull(response.getTimestamp());
        assertEquals("Failed to place order", response.getErrorMessage());
    }
}

