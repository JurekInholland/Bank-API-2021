package io.swagger.api.exception;

public class NoAccessToAccountException extends RuntimeException {
    public NoAccessToAccountException(String iban) {
        super(String.format("You are not allowed to access the account %s.", iban));
    }
}
