package com.demo.trading.event;

import com.demo.trading.common.exception.OperationException;
import com.demo.trading.common.model.events.OrderEvent;
import org.awaitility.Awaitility;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Instant;
import java.util.concurrent.CompletionException;
import java.util.concurrent.TimeUnit;

import static com.demo.trading.util.TestConstant.SUCCESS_EVENT_REQUEST;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderEventProducerTest {

    @Mock
    private KafkaTemplate<String, OrderEvent> kafkaTemplate;

    @InjectMocks
    private OrderEventProducer producer;

    @Test
    void shouldPublishEventSuccessfully() {

        OrderEvent event = new OrderEvent("user1", "order123", "ABC",
                100, "BUY", "MARKET", Instant.now());

        producer.publish(SUCCESS_EVENT_REQUEST);

        Awaitility.await().atMost(2, TimeUnit.SECONDS).untilAsserted(() ->
                verify(kafkaTemplate, times(1)).send("order-events", "order123", SUCCESS_EVENT_REQUEST)
        );
    }

    @Test
    void shouldThrowException_WhenKafkaFails() {

        doThrow(new RuntimeException("Kafka is down"))
                .when(kafkaTemplate).send(anyString(), anyString(), any(OrderEvent.class));

        CompletionException thrown = assertThrows(CompletionException.class, () ->
                producer.publish(SUCCESS_EVENT_REQUEST).join()
        );

        assertTrue(thrown.getCause() instanceof OperationException);
        assertEquals("Failed to publish the order", thrown.getCause().getMessage());
    }


}

