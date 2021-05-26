package io.swagger.api.exception;

import java.math.BigDecimal;

public class DailyLimitReachedException extends RuntimeException {
    public DailyLimitReachedException(BigDecimal dailyLimit) {
        super(String.format("This transaction would exceed your daily limit of EUR %s.", dailyLimit));
    }
}