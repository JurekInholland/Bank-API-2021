package io.swagger.service;

import io.swagger.model.Account;

import java.util.List;

public interface AccountService
{
    Account addAccount(Account account);
    List<Account> getAccounts();
}