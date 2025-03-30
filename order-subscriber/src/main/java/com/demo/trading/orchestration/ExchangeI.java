package com.demo.trading.orchestration;

import com.demo.trading.common.model.events.OrderEvent;
import com.demo.trading.model.response.ExchangeResponse;

import java.util.concurrent.CompletableFuture;

public interface ExchangeI {
    CompletableFuture<ExchangeResponse> callExchange(OrderEvent event);
}
