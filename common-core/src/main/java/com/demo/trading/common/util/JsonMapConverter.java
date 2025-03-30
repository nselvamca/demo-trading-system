package com.demo.trading.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import software.amazon.awssdk.enhanced.dynamodb.AttributeConverter;
import software.amazon.awssdk.enhanced.dynamodb.AttributeValueType;
import software.amazon.awssdk.enhanced.dynamodb.EnhancedType;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;

import java.util.Map;

public class JsonMapConverter implements AttributeConverter<Map<String, Object>> {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public AttributeValue transformFrom(Map<String, Object> input) {
        try {
            return AttributeValue.fromS(objectMapper.writeValueAsString(input));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing map", e);
        }
    }

    @Override
    public Map<String, Object> transformTo(AttributeValue input) {
        try {
            return objectMapper.readValue(input.s(), new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error deserializing map", e);
        }
    }

    @Override
    public EnhancedType<Map<String, Object>> type() {
        return EnhancedType.mapOf(String.class, Object.class);
    }

    @Override
    public AttributeValueType attributeValueType() {
        return AttributeValueType.S;
    }
}

