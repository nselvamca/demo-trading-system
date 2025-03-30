package com.demo.trading.event;

import com.demo.trading.common.exception.OperationException;
import com.demo.trading.common.model.events.OrderEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

import static com.demo.trading.common.constant.CommonConstant.ORDER_EVENT_TOPIC;


@Service
public class OrderEventProducer {

    private static final Logger log = LoggerFactory.getLogger(OrderEventProducer.class);


    private final KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public OrderEventProducer(KafkaTemplate<String, OrderEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public CompletableFuture<Void> publish(OrderEvent event) {
        return CompletableFuture.runAsync(() -> {
            try {
                kafkaTemplate.send(ORDER_EVENT_TOPIC, event.getOrderId(), event);
                log.info("Published order event: {}", event);
            } catch (Exception ex) {
                log.error("Failed to publish order event to topic {}: {}", ORDER_EVENT_TOPIC, event, ex);
                // TODO dead-letter topic (DLT)
                throw new OperationException(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Failed to publish the order");
            }
        });
    }


}
