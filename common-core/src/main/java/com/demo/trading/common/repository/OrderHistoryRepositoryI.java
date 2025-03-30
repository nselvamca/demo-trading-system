package com.demo.trading.common.repository;

import com.demo.trading.common.model.entity.OrderHistoryEntity;

import java.util.concurrent.CompletableFuture;

public interface OrderHistoryRepositoryI {

    CompletableFuture<Void> save(OrderHistoryEntity entity);
}
