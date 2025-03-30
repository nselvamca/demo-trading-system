package com.demo.trading.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExchangeResponse {
    private String status;
    private String transactionId;
    private int filledQuantity;
    private String message;
    private Instant exchangeTimestamp;
}
