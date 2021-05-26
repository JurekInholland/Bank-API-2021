package io.swagger.service;

import io.swagger.model.ModifyUserDto;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.model.UserRoles;
import io.swagger.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

//https://github.com/spring-framework-guru/sfg-blog-posts/blob/master/testingspringbootrestfulservice/src/test/java/com/springframeworkguru/Service/ProductServiceTest.java

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {


    @Mock
    private UserRepository userRepository;

    @Autowired
    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    List<User> userList = new ArrayList<>();
    ModifyUserDto modifiedUser = new ModifyUserDto("testuser111", "testln", "555555", "testuser1@example.com", "secret", new UserRoles(Role.EMPLOYEE));


    @BeforeEach
    public void setup() {

        User u = new User("testuser111", "testln", "12345", "testuser1@example.com", "secret", new UserRoles(Role.EMPLOYEE));

        testUser = u;
//        testUser.setFirstName("TestUserFirst");
//        testUser.setLastName("TestUseLast");
//        testUser.setEmailAddress("example1a@nl.nl");
//        testUser.setPhoneNumber("0625555555");
//
        userList.add(testUser);
    }

    @AfterEach
    public void tearDown() {
        testUser = null;
    }

    @Test
    public void createUser(){

        userRepository.save(testUser);
        when(userRepository.findAll()).thenReturn(userList);
        List<User> userList1 = userService.getUsers();
        assertEquals(userList1, userList);
    }

    @Test
    public void deleteUser(){

//        testUser.setId(0L);
//        userRepository.save(testUser);
//        userService.deleteUserById(0l);
//        List<User> userL = userService.getUsers();
//        System.out.println("tl"+userL);

    }

    @Test
    public void getUser(){

        userRepository.save(testUser);

        when(userRepository.findById(1L)).thenReturn(java.util.Optional.ofNullable(testUser));
        User user = userService.getUserById(1L);
        assertEquals(user, testUser);
    }

    @Test
    public void getUsers(){

        when(userRepository.findAll()).thenReturn(userList);
        List<User> userList1 = userService.getUsers();
        assertEquals(userList1, userList);
    }

    @Test
    public void updateUser(){

        userService.updateUser(modifiedUser, 1L);
        assertNotNull(modifiedUser);
    }
}
