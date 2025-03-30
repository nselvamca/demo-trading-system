package com.demo.trading.orchestration;

import com.demo.trading.common.model.events.OrderEvent;
import com.demo.trading.model.response.ExchangeResponse;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Service
public class ExchangeService implements ExchangeI {


    @Override
    public CompletableFuture<ExchangeResponse> callExchange(OrderEvent event) {
        // Simulate async mock response
        return CompletableFuture.supplyAsync(() -> ExchangeResponse.builder()
                .status("FILLED")
                .transactionId(event.getOrderId())
                .filledQuantity(event.getQuantity())
                .message("Order is Completed")
                .exchangeTimestamp(Instant.now())
                .build()
        );
    }

    /*// TODO Circuit-Breaker
    @Override
    public ExchangeResponse callExchange(OrderEvent event) {

        // Returning Mock
        return ExchangeResponse.builder()
                .status("FILLED")
                .transactionId(event.getOrderId())
                .filledQuantity(event.getQuantity())
                .message("Order is Completed")
                .exchangeTimestamp(Instant.now())
                .build();
    }*/
}
