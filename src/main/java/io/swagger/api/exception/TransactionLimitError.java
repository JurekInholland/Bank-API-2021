package io.swagger.api.exception;

import java.math.BigDecimal;

public class TransactionLimitError extends RuntimeException {
    public TransactionLimitError(BigDecimal amount, BigDecimal transactionLimit) {
        super(String.format("The transaction amount EUR %.2f would exceed your transaction limit of EUR %s", amount, transactionLimit));
    }
}