package io.swagger.service;

import io.swagger.model.Iban;

public interface IbanService
{
    Integer randNumber();
    String generateIban();
    String generateUniqueIban();
    boolean isUnique(String iban);
}
