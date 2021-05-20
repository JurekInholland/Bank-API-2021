package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.Transaction;
import java.math.BigDecimal;
import java.util.List;

public interface TransactionService
{
    Transaction addTransaction(Transaction transaction);
    Transaction executeTransaction(Account fromAccount, Account toAccount, BigDecimal amount);
    Transaction getTransactionById(int id);
    void deleteTransactionById(int id);
    List<Transaction> getTransactions();
}
