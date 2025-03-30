package com.demo.trading.validation;

import com.demo.trading.common.exception.BusinessException;
import com.demo.trading.model.request.OrderRequest;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static com.demo.trading.constant.OrderConstant.*;

@Component
public class OrderValidation {



    public void validateRequest(OrderRequest orderRequest){

       if (Objects.isNull(orderRequest)) {
           throw new BusinessException(ERROR_CODE_ORDER_REQUEST_NULL, ERROR_MSG_ORDER_REQUEST_CANNOT_BE_NULL);
       }

        if (quantityValidation(orderRequest)) {
            throw new BusinessException(ERROR_CODE_HIGHER_QUANTITY, ERROR_MSG_QUANTITY_IS_HIGHER);
        }

        if (exchangeQuantityValidation(orderRequest)) {
            throw new BusinessException(ERROR_CODE_HIGHER_QUANTITY_EXCHANGE, ERROR_MSG_QUANTITY_IS_HIGHER_FOR_THE_EXCHANGE +orderRequest.exchange());
        }
    }

    public void validateUserID(OrderRequest orderRequest){


    }

    private static boolean quantityValidation(OrderRequest orderRequest) {
        return Objects.nonNull(orderRequest)
                && !orderRequest.exchange().equalsIgnoreCase(BSE)
                && orderRequest.quantity() >= 2000;
    }

    private static boolean exchangeQuantityValidation(OrderRequest orderRequest) {
        return Objects.nonNull(orderRequest)
                && orderRequest.exchange().equalsIgnoreCase(BSE)
                && orderRequest.quantity() >= 1500;
    }
}
