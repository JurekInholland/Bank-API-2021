package io.swagger.api.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(String iban) {
        super(String.format("Account with IBAN %s was not found.", iban));
    }
}