package io.swagger.api.exception;

import java.math.BigDecimal;

public class TransactionLimitException extends RuntimeException {
    public TransactionLimitException(BigDecimal amount, BigDecimal transactionLimit) {
        super(String.format("The transaction amount EUR %.2f would exceed your transaction limit of EUR %s", amount, transactionLimit));
    }
}