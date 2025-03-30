package com.demo.trading.common.model.events;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEvent implements Serializable {
    private static final long serialVersionUID = 1L;

    private String userId;
    private String orderId;
    private String tradingSymbol;
    private int quantity;
    private String transactionType;
    private String orderType;
    private Instant timestamp;
}
