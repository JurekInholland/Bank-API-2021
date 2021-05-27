package io.swagger.service;

import io.swagger.api.exception.AccountNotFoundException;
import io.swagger.model.Account;
import io.swagger.model.ModifyAccountDto;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
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
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;

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
        return accountRepository.findById(iban).orElseThrow(() -> new AccountNotFoundException(iban));
    }

    @Override
    public void deleteAccountByIban(String iban) {
        accountRepository.deleteById(iban);
    }

    @Override
    public boolean updateAccountByIban(Account newAccount, String iban) {

        Optional<Account> updateAccount = accountRepository.findById(iban);

        if (updateAccount.isPresent()) {

            Account account = updateAccount.get();
            account.setBalance(newAccount.getBalance());
            account.setAccountType(newAccount.getAccountType());
            account.setUser(newAccount.getUser());
            accountRepository.save(account);

            return true;
        } else {
            return false;
        }


    }

    @Override
    public boolean checkUserRole(String token, String iban) {
        token = token.replace("Bearer ", ""); // Trim "Bearer" from token
        String tokenEmail = jwtUtils.getEmailFromJwtToken(token);

        try {
            Account account = this.getAccountByIban(iban);
            if (CurrentUserInfo.isEmployee()) {
                return true;
            }

            String userEmail = account.getUser().getEmailAddress();

            // See if account belongs to the requesting user
            Integer accountMatch = tokenEmail.compareTo(userEmail);
            if (accountMatch == 0) {
                return true;
            }
        } catch (AccountNotFoundException exception) {
            return false; // Account does not exist
        }

         return false;
    }
}
