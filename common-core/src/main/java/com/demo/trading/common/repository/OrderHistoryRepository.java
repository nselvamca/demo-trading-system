package com.demo.trading.common.repository;

import com.demo.trading.common.model.entity.OrderHistoryEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbAsyncTable;

import java.time.Instant;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Repository
@RequiredArgsConstructor
public class OrderHistoryRepository implements OrderHistoryRepositoryI {

    private final DynamoDbAsyncTable<OrderHistoryEntity> orderHistoryTable;

    public CompletableFuture<Void> save(OrderHistoryEntity entity) {

        return orderHistoryTable.putItem(entity)
                .whenComplete((ignored, ex) -> {
                    if (ex != null) {
                        log.error("Error saving to orderHistoryTable", ex);
                        // TODO dead-letter topic (DLT)
                    } else {
                        log.info("Saved to DynamoDB OrderID: {}", entity.getOrderId());
                    }
                });
    }
}
