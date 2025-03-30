package com.demo.trading.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonUtil {

    public static  <T> String convertToJson(T object) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.error("Failed to serialize object of type {}: {}", object.getClass().getSimpleName(), e.getMessage());
            return "{}";
        }
    }
}
