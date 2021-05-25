package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.ModifyAccountDto;

import java.util.List;

public interface AccountService
{
    Account addAccount(Account account);
    List<Account>  addAccount(List<Account> accountList);
    List<Account> getAccounts();
    Account getAccountByIban(String iban);
    Account getRandomAccount();
    void deleteAccountByIban(String iban);
    void updateAccountByIban(ModifyAccountDto modifyAccountDto, String iban);
}
