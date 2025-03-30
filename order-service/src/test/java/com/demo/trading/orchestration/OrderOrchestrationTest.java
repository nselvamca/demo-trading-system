package com.demo.trading.orchestration;

import com.demo.trading.common.exception.BusinessException;
import com.demo.trading.common.model.entity.OrderHistoryEntity;
import com.demo.trading.common.model.events.OrderEvent;
import com.demo.trading.common.repository.OrderHistoryRepository;
import com.demo.trading.event.OrderEventProducer;


import com.demo.trading.model.request.OrderRequest;
import com.demo.trading.validation.OrderValidation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static com.demo.trading.util.TestConstant.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
class OrderOrchestrationTest {

    @Mock
    private OrderEventProducer orderEventProducer;

    @Mock
    private OrderHistoryRepository orderHistoryRepository;

    @Mock
    private OrderValidation orderValidation;

    @InjectMocks
    private OrderOrchestration orderOrchestration;

    @Test
    void shouldPlaceOrderSuccessfully() {

        when(orderEventProducer.publish(any(OrderEvent.class)))
                .thenReturn(CompletableFuture.completedFuture(null));

        when(orderHistoryRepository.save(any(OrderHistoryEntity.class)))
                .thenReturn(CompletableFuture.completedFuture(null));


        CompletableFuture<String> resultFuture = orderOrchestration.placeOrder(SUCCESS_ORDER_REQUEST, USER_ID);

        assertNotNull(resultFuture);
        String result = resultFuture.join();
        assertNotNull(result);

        verify(orderValidation, times(1)).validateRequest(any(OrderRequest.class));
        verify(orderHistoryRepository, times(1)).save(any(OrderHistoryEntity.class));
        verify(orderEventProducer, times(1)).publish(any(OrderEvent.class));
    }

    @Test
    void shouldThrowBusinessException_WhenQuantityValidationFails() {

        doThrow(new BusinessException("HIGHER_QUANTITY", "Quantity is higher"))
                .when(orderValidation).validateRequest(FAILURE_HIGHER_QUANTITY);

        BusinessException thrown = assertThrows(BusinessException.class, () ->
                    orderOrchestration.placeOrder(FAILURE_HIGHER_QUANTITY, USER_ID).join());

            assertEquals("HIGHER_QUANTITY", thrown.getErrorCode());
            assertEquals("Quantity is higher", thrown.getMessage());
    }

    @Test
    void shouldThrowBusinessException_WhenQuantityForExchangeValidationFails() {
        OrderRequest request = FAILURE_HIGHER_QUANTITY_BSE;

        doThrow(new BusinessException("HIGHER_QUANTITY_EXCHANGE", "Quantity is higher, for the exchange BSE"))
                .when(orderValidation).validateRequest(argThat(req ->
                        req.exchange().equals("BSE") && req.quantity() == 2500
                ));

        BusinessException thrown = assertThrows(BusinessException.class, () ->
                orderOrchestration.placeOrder(request, USER_ID).join());

        assertEquals("HIGHER_QUANTITY_EXCHANGE", thrown.getErrorCode());
        assertEquals("Quantity is higher, for the exchange BSE", thrown.getMessage());
    }

}

