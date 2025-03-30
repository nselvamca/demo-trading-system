package com.demo.trading.enums;

public enum ExchangeStatus {
    FILLED, PARTIAL, REJECTED;

    public String toAppStatus() {
        return switch (this) {
            case FILLED -> "SUCCESS";
            case PARTIAL -> "PARTIAL_SUCCESS";
            case REJECTED -> "FAILURE";
        };
    }
}
