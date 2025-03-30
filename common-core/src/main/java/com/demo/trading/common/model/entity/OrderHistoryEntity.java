package com.demo.trading.common.model.entity;


import com.demo.trading.common.util.JsonMapConverter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbConvertedBy;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.Instant;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamoDbBean
public class OrderHistoryEntity {

    private String orderId;
    private String eventType;

    private String userId;
    private String tradingSymbol;
    private int quantity;
    private String orderType;
    private String transactionType;
    private String status;
    private String errorMessage;
    private String sourceService;
    private Map<String, Object> exchangeResponse;
    private Instant timestamp;

    @DynamoDbPartitionKey
    public String getOrderId() {
        return orderId;
    }

    @DynamoDbSortKey
    public String getEventType() {
        return eventType;
    }

    @DynamoDbConvertedBy(JsonMapConverter.class)
    public Map<String, Object> getExchangeResponse() {
        return exchangeResponse;
    }
}
