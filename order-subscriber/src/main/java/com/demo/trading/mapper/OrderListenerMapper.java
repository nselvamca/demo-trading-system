package com.demo.trading.mapper;

import com.demo.trading.common.model.entity.OrderHistoryEntity;
import com.demo.trading.common.model.events.OrderEvent;
import com.demo.trading.enums.ExchangeStatus;
import com.demo.trading.model.response.ExchangeResponse;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.Instant;
import java.util.Map;

import static com.demo.trading.constant.OrderSubscriberConstant.COMPLETED;
import static com.demo.trading.constant.OrderSubscriberConstant.ORDER_SUBSCRIBER;

public class OrderListenerMapper {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);



    public static OrderHistoryEntity exchangeResponseToOrderHistoryEntity(OrderEvent orderEvent,
                                                                          ExchangeResponse exchangeResponse) {

        Map<String, Object> responseMap = OBJECT_MAPPER.convertValue(exchangeResponse, new TypeReference<>() {});

        return OrderHistoryEntity.builder()
                .orderId(orderEvent.getOrderId())
                .eventType(COMPLETED)
                .userId(orderEvent.getUserId())
                .tradingSymbol(orderEvent.getTradingSymbol())
                .quantity(orderEvent.getQuantity())
                .transactionType(orderEvent.getTransactionType())
                .orderType(orderEvent.getOrderType())
                .sourceService(ORDER_SUBSCRIBER)
                .timestamp(Instant.now())
                .exchangeResponse(responseMap)
                .status(ExchangeStatus.valueOf(exchangeResponse.getStatus()).toAppStatus())
                .build();
    }



}
