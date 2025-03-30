package com.demo.trading.mapper;

import com.demo.trading.common.model.entity.OrderHistoryEntity;
import com.demo.trading.common.model.events.OrderEvent;
import com.demo.trading.model.request.OrderRequest;

import java.time.Instant;
import java.util.UUID;

public class OrderMapper {

    public static final String ORDER_PLACED = "PLACED";
    public static final String ORDER_SUCCESS = "SUCCESS";

    public static OrderEvent toEvent(OrderRequest request, String orderId, String userId) {
        return OrderEvent.builder()
                .userId(userId)
                .orderId(orderId)
                .tradingSymbol(request.tradingsymbol())
                .quantity(request.quantity())
                .transactionType(request.transaction_type())
                .orderType(request.order_type())
                .timestamp(Instant.now())
                .build();
    }

    public static OrderHistoryEntity toOrderHistory(OrderRequest request, String userId) {
        return OrderHistoryEntity.builder()
                .orderId(UUID.randomUUID().toString())
                .eventType(ORDER_PLACED)
                .userId(userId)
                .tradingSymbol(request.tradingsymbol())
                .quantity(request.quantity())
                .orderType(request.order_type())
                .transactionType(request.transaction_type())
                .status(ORDER_SUCCESS) // default for placed
                .sourceService("order-service")
                .timestamp(Instant.now())
                .build();
    }





}
