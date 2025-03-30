package com.demo.trading.orchestration;

import com.demo.trading.event.OrderEventProducer;
import com.demo.trading.mapper.OrderMapper;
import com.demo.trading.model.request.OrderRequest;
import com.demo.trading.common.repository.OrderHistoryRepository;
import com.demo.trading.validation.OrderValidation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderOrchestration implements OrderOrchestrationI{

    private static final Logger logger = LoggerFactory.getLogger(OrderOrchestration.class);

    private final OrderEventProducer orderEventService;
    private final OrderHistoryRepository orderHistoryRepository;
    private final OrderValidation orderValidation;

    public OrderOrchestration(OrderEventProducer orderEventService, OrderHistoryRepository orderHistoryRepository, OrderValidation orderValidation) {
        this.orderEventService = orderEventService;
        this.orderHistoryRepository = orderHistoryRepository;
        this.orderValidation = orderValidation;
    }


    public CompletableFuture<String> placeOrder(OrderRequest orderRequest, String userId) {
        logger.info("Received order request: {}", orderRequest);

        String orderId = UUID.randomUUID().toString();

        orderValidation.validateRequest(orderRequest);
        orderEventService.publish(OrderMapper.toEvent(orderRequest, orderId, userId)).join();
        orderHistoryRepository.save(OrderMapper.toOrderHistory(orderRequest, userId)).join();

        return CompletableFuture.completedFuture( orderId);
    }
}
