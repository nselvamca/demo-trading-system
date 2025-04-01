package com.demo.trading.orchestration;

import com.demo.trading.common.model.entity.OrderHistoryEntity;
import com.demo.trading.common.model.events.OrderEvent;
import com.demo.trading.common.repository.OrderHistoryRepository;
import com.demo.trading.mapper.OrderListenerMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ProcessOrder {

    @Autowired
    ExchangeService exchangeService;

    private final OrderHistoryRepository orderHistoryRepository;

    public ProcessOrder(OrderHistoryRepository orderHistoryRepository) {
        this.orderHistoryRepository = orderHistoryRepository;
    }


    @Async("orderExecutor")
    public void processAndPersist(OrderEvent event) {
        exchangeService.callExchange(event)
                .thenAccept(exchangeResponse -> {
                    log.info("Exchange response: {}", exchangeResponse);

                    OrderHistoryEntity orderHistoryEntity =
                            OrderListenerMapper.exchangeResponseToOrderHistoryEntity(event, exchangeResponse);
                    log.info("Save orderHistoryEntity: {}", orderHistoryEntity);

                    orderHistoryRepository.save(orderHistoryEntity);
                    log.info("Success DB Save for orderID: {}", event.getOrderId());

                })
                .exceptionally(ex -> {
                    log.error("Error while processing order {}: {}", event.getOrderId(), ex.getMessage(), ex);
                    // TODO: DLT or fallback
                    return null;
                });
    }


}
