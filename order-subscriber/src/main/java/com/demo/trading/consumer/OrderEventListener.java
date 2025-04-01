package com.demo.trading.consumer;

import com.demo.trading.common.model.events.OrderEvent;
import com.demo.trading.common.repository.OrderHistoryRepository;
import com.demo.trading.orchestration.ProcessOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Optional;

import static com.demo.trading.common.constant.CommonConstant.ORDER_EVENT_TOPIC;
import static com.demo.trading.constant.OrderSubscriberConstant.ORDER_EVENT_CONSUMER_GROUP;

@Component
public class OrderEventListener {


    private static final Logger log = LoggerFactory.getLogger(OrderEventListener.class);

    private final ProcessOrder processOrder;

    public OrderEventListener(ProcessOrder processOrder, OrderHistoryRepository orderHistoryRepository) {
        this.processOrder = processOrder;
    }

    @KafkaListener(topics = ORDER_EVENT_TOPIC, groupId = ORDER_EVENT_CONSUMER_GROUP)
    public void listen(OrderEvent event) {
        Optional.ofNullable(event)
                .map(OrderEvent::getOrderId)
                .ifPresentOrElse(orderId -> {
                    log.info("Processing order asynchronously: {}", orderId);
                    processOrder.processAndPersist(event);
                }, () -> log.warn("Invalid event or missing orderId"));
    }



}
