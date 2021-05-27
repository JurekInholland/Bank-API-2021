package io.swagger.service;

public interface IbanService
{
    Integer randNumber();
    String generateIban();
    String generateUniqueIban();
    boolean isUnique(String iban);
}
