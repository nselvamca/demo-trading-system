package com.demo.trading.controller;

import com.demo.trading.model.request.OrderRequest;
import com.demo.trading.orchestration.OrderOrchestration;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderOrchestration orderOrchestration;

    private final ObjectMapper objectMapper = new ObjectMapper();

    void shouldReturn201WhenOrderPlacedSuccessfully() throws Exception {
        // Arrange
        OrderRequest orderRequest = new OrderRequest(
                "Dem-1", 60, 77.0, "NFO", "NSE", "DAY",
                "BUY", "MARKET"
        );

        // Simulate async behavior using supplyAsync
        when(orderOrchestration.placeOrder(eq(orderRequest), eq("YA5488")))
                .thenReturn(CompletableFuture.supplyAsync(() -> "abc123"));

        // First perform and expect async started
        MvcResult mvcResult = mockMvc.perform(post("/orders/placeorder")
                        .header("user-id", "YA5488")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(request().asyncStarted())
                .andReturn();

        // Now perform the async dispatch and validate response
        mockMvc.perform(asyncDispatch(mvcResult))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value("Success"))
                .andExpect(jsonPath("$.orderId").value("abc123"))
                .andExpect(jsonPath("$.timestamp").exists());
    }



}
