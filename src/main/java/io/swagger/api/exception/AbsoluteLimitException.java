package io.swagger.api.exception;

import java.math.BigDecimal;

public class AbsoluteLimitException extends RuntimeException {
    public AbsoluteLimitException(BigDecimal amount, BigDecimal balance, BigDecimal absoluteLimit) {
        super(String.format("The transaction amount EUR %.2f would set your balance of EUR %s below your absolute limit of EUR %s", amount, balance,absoluteLimit));
    }
}