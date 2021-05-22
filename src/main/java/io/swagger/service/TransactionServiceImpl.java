package io.swagger.service;

import io.swagger.api.exception.TransactionNotFoundException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Override
    public Transaction addTransaction(Transaction transaction) {
        return transactionRepository.save(transaction);
    }

    @Override
    public Transaction executeTransaction(Account fromAccount, Account toAccount, BigDecimal amount) {
        return null;
    }

    @Override
    public Transaction getTransactionById(int id) {
        return transactionRepository.findById(id).orElseThrow(TransactionNotFoundException::new);
    }

    @Override
    public void deleteTransactionById(int id) {
        if (transactionRepository.findById(id) != null) {
            transactionRepository.deleteById(id);
        }

    }

    @Override
    public List<Transaction> getTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }
}
