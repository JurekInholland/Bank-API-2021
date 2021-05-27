package io.swagger.service;

import io.swagger.api.exception.UserNotFoundException;
import io.swagger.model.CreateUserDto;
import io.swagger.model.ModifyUserDto;
import io.swagger.model.User;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.TransactionRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UserServiceImpl implements UserService
{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    PasswordEncoder encoder;

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
    public void updateUser(ModifyUserDto modifyUserDto, long id)
    {
        User user = userRepository.findById(id).orElseThrow(UserNotFoundException::new);

        if (modifyUserDto.getFirstName() != null && !modifyUserDto.getFirstName().isEmpty()) {
            user.setFirstName(modifyUserDto.getFirstName());
        }
        if (modifyUserDto.getLastName() != null && !modifyUserDto.getLastName().isEmpty()) {
            user.setLastName(modifyUserDto.getLastName());
        }
        if (modifyUserDto.getEmailAddress() != null && !modifyUserDto.getEmailAddress().isEmpty()) {
            user.setEmailAddress(modifyUserDto.getEmailAddress());
        }
        if (modifyUserDto.getRoles() != null && !modifyUserDto.getRoles().isEmpty()) {
            user.setRoles(modifyUserDto.getRoles());
        }
        if (modifyUserDto.getPassword() != null && !modifyUserDto.getPassword().isEmpty()) {
            user.setPassword(encoder.encode(modifyUserDto.getPassword()));
        }
        if (modifyUserDto.getPhoneNumber() != null && !modifyUserDto.getPhoneNumber().isEmpty()) {
            user.setPassword(modifyUserDto.getPhoneNumber());
        }

        userRepository.save(user);
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