package io.swagger.service;

import io.swagger.api.exception.TransactionNotFoundException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
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
    public Transaction getTransactionById(long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new TransactionNotFoundException(id));
    }

    @Override
    public void deleteTransactionById(long id) {
        if (transactionRepository.findById(id) != null) {
            transactionRepository.deleteById(id);
        }
        else throw new TransactionNotFoundException(id);

    }

    @Override
    public List<Transaction> getTransactions() {
        return (List<Transaction>) transactionRepository.findAll();
    }

    @Override
    public List<Transaction> getUserTransactions(User user) {
        return transactionRepository.findByUserPerforming_Id(user.getId());
    }
}
