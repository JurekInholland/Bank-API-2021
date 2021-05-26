package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.model.AccountType;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.TransactionRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.threeten.bp.OffsetDateTime;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;


//https://github.com/spring-framework-guru/sfg-blog-posts/blob/master/testingspringbootrestfulservice/src/test/java/com/springframeworkguru/Service/ProductServiceTest.java

@ExtendWith(MockitoExtension.class)
class TransactionServiceImplTest {

    private String testIban1 = "TESTIBAN001";
    private String testIban2 = "TESTIBAN002";

    private Account testAccountFrom;
    private Account testAccountTo;

    private ArrayList<Role> roles = new ArrayList<>();

    @Mock
    private TransactionRepository transactionRepository;

    @Autowired
    @InjectMocks
    private TransactionServiceImpl transactionService;

    private User testUser;
    private Transaction transaction1;
    List<Transaction> transactionList = new ArrayList<>();

    @BeforeEach
    public void setup() {

        roles.add(Role.EMPLOYEE);
        User u = new User("testuser111","testln","12345","testuser1@example.com","secret", roles);

        testUser = u;
        testAccountFrom = new Account(testIban1, testUser, BigDecimal.valueOf(1000), AccountType.CURRENT);
        testAccountTo = new Account(testIban1, testUser, BigDecimal.valueOf(1000), AccountType.CURRENT);

        transaction1 = new Transaction();
        transaction1.setAccountFrom(testAccountFrom);
        transaction1.setAccountTo(testAccountTo);
        transaction1.setUserPerforming(testUser);
        transaction1.setAmount(BigDecimal.valueOf(100));

        transactionList.add(transaction1);
    }

    @AfterEach
    public void tearDown() {
        testUser = null;
        testAccountFrom = null;
        testAccountTo = null;
        transaction1 = null;
    }

    @Test
    void addTransaction() {
        transaction1.setTimestamp(OffsetDateTime.now());
        transactionRepository.save(transaction1);
        when(transactionRepository.findAll()).thenReturn(transactionList);
        List<Transaction> transactionList1 = transactionService.getTransactions();
        assertEquals(transactionList1, transactionList);
    }

    @Test
    void executeTransaction() {
        transaction1.execute();
        assertNotNull(transaction1.getTimestamp());
    }

    @Test
    void getTransactionById() {
        transaction1.setTimestamp(OffsetDateTime.now());
        transactionRepository.save(transaction1);

        when(transactionRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(transaction1));
        Transaction transaction = transactionService.getTransactionById(1);
        assertEquals(transaction, transaction1);

    }

    @Test
    void deleteTransactionById() {

        transaction1.setTimestamp(OffsetDateTime.now());
        transaction1.setId(0);
        transactionRepository.save(transaction1);
        transactionService.deleteTransactionById(0);
        List<Transaction> transactionL = transactionService.getTransactions();
        System.out.println("tl"+transactionL);
    }

    @Test
    void getTransactions() {
        when(transactionRepository.findAll()).thenReturn(transactionList);
        List<Transaction> transactionList1 = transactionService.getTransactions();
        assertEquals(transactionList1, transactionList);
    }

    @Test
    void getUserTransactions() {

        transactionService.getUserTransactions(testUser);
    }
}