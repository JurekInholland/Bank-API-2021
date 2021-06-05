package io.swagger.service;

import io.swagger.model.Account;

import java.util.List;


public interface AccountService
{
    Account addAccount(Account account);
    List<Account>  addAccount(List<Account> accountList);
    List<Account> getAccounts();
    Account getAccountByIban(String iban);
    void deleteAccountByIban(String iban);
    void updateAccountByIban(Account newAccount, String iban);


}
