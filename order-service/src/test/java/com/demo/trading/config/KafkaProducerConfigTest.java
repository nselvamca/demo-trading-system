package com.demo.trading.config;

import com.demo.trading.common.model.events.OrderEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.lang.reflect.Field;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class KafkaProducerConfigTest {

    private KafkaProducerConfig kafkaProducerConfig;
    private KafkaProperties kafkaProperties;

    @BeforeEach
    void setUp() throws Exception {
        kafkaProperties = mock(KafkaProperties.class);
        kafkaProducerConfig = new KafkaProducerConfig();

        Field field = KafkaProducerConfig.class.getDeclaredField("kafkaProperties");
        field.setAccessible(true);
        field.set(kafkaProducerConfig, kafkaProperties);
    }


    @Test
    void testProducerFactory() {
        when(kafkaProperties.getBootstrapServers()).thenReturn(Collections.singletonList("localhost:9092"));

        ProducerFactory<String, OrderEvent> factory = kafkaProducerConfig.producerFactory();
        assertNotNull(factory);
    }

    @Test
    void testKafkaTemplate() {
        when(kafkaProperties.getBootstrapServers()).thenReturn(Collections.singletonList("localhost:9092"));

        KafkaTemplate<String, OrderEvent> template = kafkaProducerConfig.kafkaTemplate();
        assertNotNull(template);
    }
}
