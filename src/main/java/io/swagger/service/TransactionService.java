package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;

import java.math.BigDecimal;
import java.util.List;

public interface TransactionService
{
    Transaction addTransaction(Transaction transaction);
    Transaction executeTransaction(Account fromAccount, Account toAccount, BigDecimal amount);
    Transaction getTransactionById(long id);
    void deleteTransactionById(long id);
    List<Transaction> getTransactions();

    List<Transaction> getUserTransactions(User user);
}
