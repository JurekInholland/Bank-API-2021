package io.swagger.service;

import io.swagger.model.User;
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

    public User addUser(User user)
    {
        userRepository.save(user);
        return user;
    }
    public User getUserById(long id)
    {
        return userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
    }
    public List<User> getUsers()
    {
        return (List<User>) userRepository.findAll();
    }
    public void deleteUserById(long id)
    {
        userRepository.deleteById(id);
    }
}