package io.swagger.api.exception;

public class InvalidTransactionAmountException extends RuntimeException {
    public InvalidTransactionAmountException(String amount) {
        super(String.format("Transaction amount %s is invalid.", amount));
    }
}