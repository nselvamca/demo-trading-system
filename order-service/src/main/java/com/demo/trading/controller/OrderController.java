package com.demo.trading.controller;

import com.demo.trading.mapper.OrderResponseMapper;
import com.demo.trading.model.request.OrderRequest;
import com.demo.trading.model.response.OrderResponse;
import com.demo.trading.orchestration.OrderOrchestration;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

import static com.demo.trading.constant.OrderConstant.USER_ID;
import static com.demo.trading.constant.OrderConstant.USER_ID_IS_REQUIRED;

@RestController
@RequestMapping("${app.base-path}/orders")
public class OrderController {


    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);


    private final OrderOrchestration orderOrchestration;

    public OrderController(OrderOrchestration orderOrchestration) {
        this.orderOrchestration = orderOrchestration;
    }

    @PostMapping("/placeorder")
    public CompletableFuture<ResponseEntity<OrderResponse>> placeOrder(
            @Valid @RequestBody OrderRequest orderRequest,
            @RequestHeader(USER_ID) String userId) {

        logger.info("Received order request: {}", orderRequest);

        return orderOrchestration.placeOrder(orderRequest, userId)
                .thenApply(orderId -> ResponseEntity.status(HttpStatus.CREATED).
                        body(OrderResponseMapper.successResponse(orderId)))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(OrderResponseMapper.failureResponse()));
    }


}
