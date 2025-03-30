package com.demo.trading.model.request;

import jakarta.validation.constraints.*;

public record OrderRequest(
        @NotNull(message = "Product is required")
        String product,

        @Min(value = 1, message = "Quantity must be greater than 0")
        @NotNull(message = "Quantity is required")
        int quantity,

        @DecimalMin(value = "0.0", inclusive = true, message = "Price cannot be negative")
        @NotNull(message = "Price is required")
        double price,

        @NotNull(message = "Trading symbol is required")
        String tradingsymbol,

        @Pattern(regexp = "NSE|BSE|NFO", message = "Exchange must be NSE, BSE, or NFO")
        @NotNull(message = "Exchange is required")
        String exchange,

        @Pattern(regexp = "DAY|IOC", message = "Validity must be DAY or IOC")
        String validity,

        @Pattern(regexp = "BUY|SELL", message = "Transaction type must be BUY or SELL")
        @NotNull(message = "Transaction-Type is required")
        String transaction_type,

        @Pattern(regexp = "MARKET|LIMIT", message = "Order type must be MARKET or LIMIT")
        @NotNull(message = "Order_Type is required")
        String order_type
) {}
