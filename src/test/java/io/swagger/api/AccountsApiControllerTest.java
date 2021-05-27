package io.swagger.api;

import org.junit.jupiter.api.Test;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Account;
import io.swagger.model.AccountType;
import io.swagger.model.Role;
import io.swagger.model.User;
import io.swagger.model.JwtResponse;
import io.swagger.service.AccountService;
import io.swagger.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import org.threeten.bp.OffsetDateTime;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class AccountsApiControllerTest {

    private static final String testIban1 = "TESTIBAN001";
    private static final String testIban2 = "TESTIBAN002";
    private static final Integer userId = 01;
    //private static final BigDecimal ballance = new BigDecimal("1000.40");
    private static final AccountType accountType1 = AccountType.CURRENT;
    private static final AccountType accountType2 = AccountType.SAVINGS;
    private static final ArrayList<Role> roles = new ArrayList<>();

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
    private Account testDestinationAccount;

    private String jwtToken;

    public User createMockUser() {
        roles.add(Role.EMPLOYEE);
        User u = new User("testuser","testln","12345","testuser@example.com",encoder.encode("secret"), roles);

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
        testDestinationAccount = createMockAccount(testUser,testIban2, BigDecimal.valueOf(1000), AccountType.SAVINGS);

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
        testDestinationAccount = null;
    }

    @Test
    void createAccount() throws Exception {
        mvc.perform(post("/accounts")
                .header("Authorization", "Bearer " + this.jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"iban\": \"%s\", \"userId\": \"%s\", \"accountType\": \"%s\"}",testIban1, userId, accountType1)))
                .andExpect(status().isOk());

        MvcResult res = mvc.perform(post("/accounts")
                .header("Authorization", "Bearer " + this.jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"iban\": \"%s\", \"userId\": \"%s\", \"accountType\": \"%s\"}",testIban1, userId, accountType1)))
                .andExpect(status().is4xxClientError()).andReturn();
        System.out.println(res);
        System.out.println("asd");

    }

    @Test
    void deleteAccount() throws Exception {
        Account mockAccount = createMockAccount(testUser,testIban1, BigDecimal.valueOf(1000), AccountType.CURRENT);

        mvc.perform(delete("/accounts/" + mockAccount.getUser().getId())
                .header("Authorization", "Bearer " + this.jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());


    }

    @Test
    void getAccount() throws Exception {
        Account mockAccount = createMockAccount(testUser,testIban1, BigDecimal.valueOf(1000), AccountType.CURRENT);

        mvc.perform((get("/accounts/" + mockAccount.getUser().getId())
                .header("Authorization", "Bearer " + this.jwtToken))
        ).andExpect(status().isOk());
    }

    @Test
    void getAccounts() throws Exception {
        mvc.perform((get("/accounts/")
                .header("Authorization", "Bearer " + this.jwtToken))
        ).andExpect(status().isOk());
    }

    @Test
    void updateAccount() throws Exception {
        Account mockAccount = createMockAccount(testUser,testIban1, BigDecimal.valueOf(1000), AccountType.CURRENT);

        mvc.perform((put("/accounts/" + mockAccount.getUser().getId())
                .header("Authorization", "Bearer " + this.jwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"iban\": \"%s\", \"userId\": \"%s\", \"accountType\": \"%s\"}",testIban1, userId, accountType1)))
        .andExpect(status().isOk());
    }
}