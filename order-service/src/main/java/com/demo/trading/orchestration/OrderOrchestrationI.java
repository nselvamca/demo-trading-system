package com.demo.trading.orchestration;

import com.demo.trading.model.request.OrderRequest;

import java.util.concurrent.CompletableFuture;

public interface OrderOrchestrationI {
    public CompletableFuture<String> placeOrder(OrderRequest orderRequest, String userId);
}
