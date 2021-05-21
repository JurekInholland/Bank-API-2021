package io.swagger.service;

import io.swagger.api.exception.UserNotFoundException;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.ToLongFunction;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    public User addUser(User user)
    {
        userRepository.save(user);
        return user;
    }
    public User getUserById(long id)
    {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }
    public List<User> getUsers()
    {
        return (List<User>) userRepository.findAll();
    }
    public void deleteUserById(long id)
    {

//        Fix regression: To avoid ConstraintViolationExceptions,
//        before deleting a user, delete their transactions and accounts first.

        transactionRepository.findAll().forEach(transaction -> {
            if (transaction.getUserPerforming().getId() == id || transaction.getAccountFrom().getUser().getId() == id || transaction.getAccountTo().getUser().getId() == id) {
                transactionRepository.deleteById(transaction.getId());
            }
        });

        accountRepository.findAll().forEach(account -> {
            if (account.getUser().getId() == id) {
                accountRepository.deleteById(account.getIban());
            }
        });

        userRepository.deleteById(id);
    }
}