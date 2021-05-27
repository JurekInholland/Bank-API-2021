package io.swagger.service;

import io.swagger.api.exception.AccountNotFoundException;
import io.swagger.model.Account;
import io.swagger.model.ModifyAccountDto;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import io.swagger.security.jwt.JwtUtils;
import io.swagger.util.CurrentUserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service

public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    /**
     * Add an account to the database.
     *
     * @param account Account model to store
     * @return Account model that was stored
     */
    @Override
    public Account addAccount(Account account) {
        accountRepository.save(account);
        return account;
    }

    /**
     * Add a list of account to the database
     *
     * @param accountList List of Account models to store
     * @return List Account models that were stored
     */
    public List<Account> addAccount(List<Account> accountList) {
        accountRepository.saveAll(accountList);
        return accountList;
    }

    @Override
    public List<Account> getAccounts() {
        return (List<Account>) accountRepository.findAll();
    }


    @Override
    public Account getAccountByIban(String iban) {
        return accountRepository.findById(iban).orElseThrow(() -> new  AccountNotFoundException(iban));
    }

    @Override
    public void deleteAccountByIban(String iban) {

//        Delete transactions belonging to account
        transactionRepository.findAll().forEach(transaction -> {
            if (transaction.getAccountTo().getIban().equals(iban) || transaction.getAccountFrom().getIban().equals(iban)) {
                transactionRepository.deleteById(transaction.getId());
            }
        });

        accountRepository.deleteById(iban);
    }

    @Override
    public void updateAccountByIban(Account newAccount, String iban) {

        Optional<Account> updateAccount = accountRepository.findById(iban);
        Account account = updateAccount.get();
        account.setBalance(newAccount.getBalance());
        account.setAccountType(newAccount.getAccountType());
        account.setUser(newAccount.getUser());
        accountRepository.save(account);
    }

}
