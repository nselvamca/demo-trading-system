package com.demo.trading.mapper;

import com.demo.trading.common.model.entity.OrderHistoryEntity;
import com.demo.trading.common.model.events.OrderEvent;
import com.demo.trading.model.request.OrderRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrderMapperTest {

    private static final OrderRequest VALID_REQUEST = new
            OrderRequest("MIS", 60, 100.0, "INFY", "NSE",
            "DAY", "BUY", "MARKET");


    private static final String USER_ID = "user123";
    private static final String ORDER_ID = "order123";

    @Test
    void shouldMapToOrderEvent() {
        OrderEvent event = OrderMapper.toEvent(VALID_REQUEST, ORDER_ID, USER_ID);

        assertNotNull(event);
        assertEquals(USER_ID, event.getUserId());
        assertEquals(ORDER_ID, event.getOrderId());
        assertEquals("INFY", event.getTradingSymbol());
        assertEquals(60, event.getQuantity());
        assertEquals("BUY", event.getTransactionType());
        assertEquals("MARKET", event.getOrderType());
        assertNotNull(event.getTimestamp());
    }

    @Test
    void shouldMapToOrderHistoryEntity() {
        OrderHistoryEntity history = OrderMapper.toOrderHistory(VALID_REQUEST, USER_ID);

        assertNotNull(history);
        assertNotNull(history.getOrderId()); // generated UUID
        assertEquals("PLACED", history.getEventType());
        assertEquals(USER_ID, history.getUserId());
        assertEquals("INFY", history.getTradingSymbol());
        assertEquals(60, history.getQuantity());
        assertEquals("BUY", history.getTransactionType());
        assertEquals("MARKET", history.getOrderType());
        assertEquals("SUCCESS", history.getStatus());
        assertEquals("order-service", history.getSourceService());
        assertNotNull(history.getTimestamp());
    }
}


