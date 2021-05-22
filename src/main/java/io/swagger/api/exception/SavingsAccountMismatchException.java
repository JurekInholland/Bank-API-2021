package io.swagger.api.exception;

import java.math.BigDecimal;

public class SavingsAccountMismatchException extends RuntimeException {
    public SavingsAccountMismatchException(String message) {
        super(message);
    }
}