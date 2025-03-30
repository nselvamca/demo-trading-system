package com.demo.trading.util;

import com.demo.trading.common.model.events.OrderEvent;
import com.demo.trading.model.request.OrderRequest;

import java.time.Instant;

public class TestConstant {

    public static String USER_ID = "YA5488";

    public static OrderRequest SUCCESS_ORDER_REQUEST =  new OrderRequest(
                "Google", 60, 77.0, "NFO", "NSE",
            "BUY", "MARKET", "MIS");

    public static OrderRequest SUCCESS_ORDER_BSE =  new OrderRequest(
            "Google", 1400, 77.0, "NFO", "BSE",
            "BUY", "MARKET", "MIS");

    public static OrderRequest FAILURE_HIGHER_QUANTITY =  new OrderRequest(
            "Google", 3000, 77.0, "NFO", "NSE",
            "BUY", "MARKET", "MIS");

    public static OrderRequest FAILURE_HIGHER_QUANTITY_BSE =  new OrderRequest(
            "Boeing", 2500, 77.0, "NFO", "BSE",
            "BUY", "MARKET", "MIS");

    public static OrderEvent SUCCESS_EVENT_REQUEST = new OrderEvent("user1", "order123",
            "ABC", 100,
            "BUY", "MARKET", Instant.now());


}
