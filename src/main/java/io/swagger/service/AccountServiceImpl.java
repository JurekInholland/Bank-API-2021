package io.swagger.service;

import io.swagger.api.exception.AccountNotFoundException;
import io.swagger.model.Account;
import io.swagger.model.ModifyAccountDto;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service

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

    @Override
    public Account getAccountByIban(String iban) {
        return accountRepository.findById(iban).orElseThrow(AccountNotFoundException::new);
    }

    @Override
    public Account getRandomAccount() {
        // TODO: This needs to be removed when we no longer need seeding
        // Turn Iterator to List
        List<Account> accountList = new ArrayList<>();
        accountRepository.findAll().forEach(accountList::add);

        // Retrieve a random Account from the list
        Random rand = new Random();
        Integer randomIndex = rand.nextInt(accountList.size());
        Account randomAccount = accountList.get(randomIndex);

        return randomAccount;
    }

    @Override
    public void deleteAccountByIban(String iban) {
        accountRepository.deleteById(iban);
    }

    @Override
    public void updateAccountByIban(ModifyAccountDto modifyAccountDto, String iban) {
        accountRepository.updateAccountByIban(modifyAccountDto.getIban(), modifyAccountDto.getBalance(), modifyAccountDto.getAccountType(), modifyAccountDto.getUserId());
    }
}
