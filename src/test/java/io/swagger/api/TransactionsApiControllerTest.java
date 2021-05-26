package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.model.Account;
import io.swagger.model.AccountType;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.model.JwtResponse;
import io.swagger.service.AccountService;
import io.swagger.service.TransactionService;
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
import org.threeten.bp.OffsetDateTime;

import java.math.BigDecimal;
import java.util.ArrayList;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TransactionsApiControllerTest {


    private static final String testIban1 = "TESTIBAN001";
    private static final String testIban2 = "TESTIBAN002";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private TransactionService transactionService;

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
        ArrayList<Role> roles = new ArrayList<>();
        roles.add(Role.EMPLOYEE);

        User u = new User("testuser","testln","12345","testuser@example.com",encoder.encode("secret"), roles);

        return userService.addUser(u);
    }

    public Account createMockAccount(User user, String iban, BigDecimal balance, AccountType accountType) {
        Account account = new Account(iban, user, balance, accountType);
        accountService.addAccount(account);
        return account;
    }

    public Transaction createMockTransaction(Account accountFrom, Account accountTo, User userPerforming) {
        Transaction transaction = new Transaction();
        transaction.setUserPerforming(testUser);
        transaction.setAccountFrom(testAccount);
        transaction.setAccountTo(testDestinationAccount);
        transaction.setAmount(BigDecimal.valueOf(100));
        transaction.setTimestamp(OffsetDateTime.now());

        return transactionService.addTransaction(transaction);
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
    public void createTransaction() throws Exception {

        mvc.perform(post("/transactions")
            .header("Authorization", "Bearer " + this.jwtToken)
            .contentType(MediaType.APPLICATION_JSON)
            .content(String.format("{\"fromIban\": \"%s\", \"toIban\": \"%s\", \"amount\": \"100\"}",testIban1, testIban2)))
            .andExpect(status().isOk());

        MvcResult res = mvc.perform(post("/transactions")
                .header("Authorization", "Bearer " + this.jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(String.format("{\"fromIban\": \"%s\", \"toIban\": \"%s\", \"amount\": \"100\"}",testIban1, testIban1)))
                .andExpect(status().is4xxClientError()).andReturn();
        System.out.println(res);
        System.out.println("asd");
    }

    @Test
    public void deleteTransaction() throws Exception {
        Transaction mockTransaction = createMockTransaction(testAccount, testDestinationAccount, testUser);

        mvc.perform(delete("/transactions/" + mockTransaction.getId())
                .header("Authorization", "Bearer " + this.jwtToken)
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }

    @Test
    public void getTransactionById() throws Exception {
        Transaction mockTransaction = createMockTransaction(testAccount, testDestinationAccount, testUser);

        mvc.perform((get("/transactions/" + mockTransaction.getId())
                .header("Authorization", "Bearer " + this.jwtToken))
        ).andExpect(status().isOk());
    }

    @Test
    public void getTransactions() throws Exception {
        mvc.perform((get("/transactions/")
                .header("Authorization", "Bearer " + this.jwtToken))
        ).andExpect(status().isOk());
    }

    @Test
    public void updateTransaction() throws Exception {
        Transaction mockTransaction = createMockTransaction(testAccount, testDestinationAccount, testUser);

        mvc.perform((put("/transactions/" + mockTransaction.getId())
                .header("Authorization", "Bearer " + this.jwtToken))
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"amount\": \"100\"}")
        ).andExpect(status().isOk());
    }
}