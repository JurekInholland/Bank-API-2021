package io.swagger.api.exception;

public class TransactionNotFoundException extends RuntimeException {
    public TransactionNotFoundException(Integer transactionId) {
        super( String.format("A transaction with id %s does not exist.", transactionId));
    }
}
