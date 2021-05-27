package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.*;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsersApiControllerTest {

    private static final String testIban1 = "TESTIBAN001";
    private static final String testIban2 = "TESTIBAN002";
    private static final String firstName = "TESTFIRSTNAME";
    private static final String lastName = "TESTLASTNAME";
    private static final String phoneNumber = "0123456789";
    private static final String emailAddress = "example@test.nl";
    private static final String password = "examplePassword";
    private static final Role role = Role.CUSTOMER;

    private ArrayList<Role> roles = new ArrayList<>();

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;


    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;
    private Account testAccount;

    private String jwtToken;

    public User createMockUser() {

        roles.add(Role.EMPLOYEE);
        User u = new User("testuser","testln","12345","testuser@example.com",encoder.encode("secret"), roles);

        return userService.addUser(u);
    }

    public User createTestUser() {
        User u = new User(firstName,lastName,phoneNumber,emailAddress,encoder.encode(password), roles);
        return userService.addUser(u);

    }



    public Account createMockAccount(User user, String iban, BigDecimal balance, AccountType accountType) {
        Account account = new Account(iban, user, balance, accountType);
        accountService.addAccount(account);
        return account;
    }

    @BeforeEach
    public void init() throws Exception {
        testUser = createMockUser();
        testAccount = createMockAccount(testUser,testIban1, BigDecimal.valueOf(1000), AccountType.CURRENT);

        //        Login as test employee
        MvcResult result = mvc.perform(post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"email_address\": \"testuser@example.com\", \"password\": \"secret\"}"))
                .andExpect(status().isOk()
                ).andReturn();
        String contentAsString = result.getResponse().getContentAsString();

        JwtResponse loginResponse = objectMapper.readValue(contentAsString, JwtResponse.class);

//        Store token
        this.jwtToken = loginResponse.getToken();
    }

    @AfterEach
    public void tearDown() {

        userService.deleteUserById(testUser.getId());
//        TODO: Fix when implemented
//        accountService.deleteAccount()

        testUser = null;
        testAccount = null;
    }

    @Test
    void createUser() throws Exception{

        mvc.perform(post("/users")
                .header("Authorization", "Bearer " + this.jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"id\": \"01\", \"firstName\": \"%s\", \"lastName\": \"100\", \"phoneNumber\": \"%s\", \"emailAddress\": \"%s\",\"password\": \"%s\", \"roles\": \"%s\"}",firstName, lastName, phoneNumber, emailAddress,password,role)))
                .andExpect(status().isOk());

        MvcResult res = mvc.perform(post("/users")
                .header("Authorization", "Bearer " + this.jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"id\": \"01\", \"firstName\": \"%s\", \"lastName\": \"100\", \"phoneNumber\": \"%s\", \"emailAddress\": \"%s\",\"password\": \"%s\", \"roles\": \"%s\"}",firstName,lastName, phoneNumber, emailAddress,password,role)))
                .andExpect(status().is4xxClientError()).andReturn();
        System.out.println(res);
        System.out.println("asd");
        
    }

    @Test
    void deleteUser() throws Exception {
        User mockUser = createTestUser();
//
        mvc.perform(delete("/users/" + mockUser.getId())
                .header("Authorization", "Bearer " + this.jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    void getUser() throws Exception {
        User mockUser = createTestUser();

        mvc.perform((get("/users/" + mockUser.getId())
                .header("Authorization", "Bearer " + this.jwtToken))
        ).andExpect(status().isOk());
    }

    @Test
    void getUsers() throws Exception {
        mvc.perform((get("/users/")
                .header("Authorization", "Bearer " + this.jwtToken))
        ).andExpect(status().isOk());
    }

    @Test
    void updateUser() throws Exception {
        User mockUser = createTestUser();

        mvc.perform((put("/users/" + mockUser.getId())
                .header("Authorization", "Bearer " + this.jwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"id\": \"01\", \"firstName\": \"%s\", \"lastName\": \"100\", \"phoneNumber\": \"%s\", \"emailAddress\": \"%s\",\"password\": \"%s\", \"roles\": \"%s\"}",firstName,lastName, phoneNumber, emailAddress,password,role)))
        .andExpect(status().isOk());
    }
}