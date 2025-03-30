package com.demo.trading.filter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.MDC;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CorrelationInterceptorTest {

    private CorrelationInterceptor interceptor;
    private HttpServletRequest request;
    private HttpServletResponse response;

    @BeforeEach
    void setUp() {
        interceptor = new CorrelationInterceptor();
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);
    }

    @Test
    void shouldUseProvidedCorrelationIdFromHeader() {
        String existingCorrelationId = "abc-123-id";
        when(request.getHeader("X-Correlation-ID")).thenReturn(existingCorrelationId);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(response).setHeader("X-Correlation-ID", existingCorrelationId);
        assertEquals(existingCorrelationId, MDC.get("X-Correlation-ID"));
    }

    @Test
    void shouldGenerateNewCorrelationIdIfHeaderMissing() {
        when(request.getHeader("X-Correlation-ID")).thenReturn(null);

        boolean result = interceptor.preHandle(request, response, new Object());

        assertTrue(result);
        verify(response).setHeader(eq("X-Correlation-ID"), anyString());
        assertNotNull(MDC.get("X-Correlation-ID"));
    }

    @Test
    void shouldRemoveCorrelationIdAfterCompletion() {
        MDC.put("X-Correlation-ID", "dummy");
        interceptor.afterCompletion(request, response, new Object(), null);

        assertNull(MDC.get("X-Correlation-ID"));
    }
}
