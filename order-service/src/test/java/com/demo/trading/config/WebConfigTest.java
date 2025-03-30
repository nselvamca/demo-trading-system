package com.demo.trading.config;

import com.demo.trading.filter.CorrelationInterceptor;
import com.demo.trading.orchestration.OrderOrchestration;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(WebConfig.class)
class WebConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CorrelationInterceptor correlationInterceptor;

    @MockBean
    private OrderOrchestration orderOrchestration;

    @Test
    void shouldRegisterInterceptor() throws Exception {
        mockMvc.perform(get("/any-endpoint"));
        verify(correlationInterceptor, atLeastOnce()).preHandle(any(), any(), any());
    }
}

