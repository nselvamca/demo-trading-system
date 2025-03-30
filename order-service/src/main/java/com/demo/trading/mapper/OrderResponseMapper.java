package com.demo.trading.mapper;

import com.demo.trading.model.response.OrderResponse;

import java.time.Instant;

import static com.demo.trading.constant.OrderConstant.RESPONSE_FAILURE;
import static com.demo.trading.constant.OrderConstant.RESPONSE_SUCCESS;

public class OrderResponseMapper {



    public static OrderResponse successResponse(String orderId){

        return OrderResponse.builder()
                .status(RESPONSE_SUCCESS)
                .orderId(orderId)
                .timestamp(Instant.now())
                .build();

    }

    public static OrderResponse failureResponse(){

        return OrderResponse.builder()
                .status(RESPONSE_FAILURE)
                .errorMessage("Failed to place order")
                .timestamp(Instant.now())
                .build();

    }


}
