package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
// TODO: This is breaking the application
/*

 */
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

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
}
