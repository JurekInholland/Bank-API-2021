package io.swagger.service;

import io.swagger.model.*;
import io.swagger.repository.AccountRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

//https://github.com/spring-framework-guru/sfg-blog-posts/blob/master/testingspringbootrestfulservice/src/test/java/com/springframeworkguru/Service/ProductServiceTest.java

@ExtendWith(MockitoExtension.class)
public class AccountServiceImplTest {

    @Mock
    private AccountRepository accountRepository;

    @Autowired
    @InjectMocks
    private AccountServiceImpl accountService;
    private Account account;

    List<Account> accountList = new ArrayList<>();
    private ArrayList<Role> roles = new ArrayList<>();
    ModifyAccountDto modifyAccount;
    private User testUser;
    private Account testAccount;
    private String testIban1 = "TESTIBAN001";
    private String testIban2 = "TESTIBAN002";

    @BeforeEach
    public void setup() {

        roles.add(Role.EMPLOYEE);
        User u = new User("testuser111","testln","12345","testuser1@example.com","secret", roles);

        testUser = u;
        testAccount = new Account(testIban1, testUser, BigDecimal.valueOf(1000), AccountType.CURRENT);

        accountList.add(testAccount);
    }

    @AfterEach
    public void tearDown() {
        testUser = null;
        testAccount = null;
    }


    @Test
    public void addAccount(){
        accountRepository.save(testAccount);
        when(accountRepository.findAll()).thenReturn(accountList);
        List<Account> accountList1 = accountService.getAccounts();
        assertEquals(accountList1, accountList);
    }

    @Test
    public void getAccounts(){
        when(accountRepository.findAll()).thenReturn(accountList);
        List<Account> accountList1 = accountService.getAccounts();
        assertEquals(accountList1, accountList);
    }

    @Test
    public void getAccountByIban(){
        accountRepository.save(testAccount);

        when(accountRepository.findById("NL05")).thenReturn(java.util.Optional.ofNullable(testAccount));
        Account account = accountService.getAccountByIban("NL05");
        assertEquals(account, testAccount);
    }

    @Test
    public void deleteAccountByIban(){
        testUser.setId(0L);
        accountRepository.save(testAccount);
        accountService.deleteAccountByIban("NL05");
        List<Account> accountL = accountService.getAccounts();
        System.out.println("tl"+accountL);
    }

    @Test
    public void updateAccountByIban(){

        accountService.updateAccountByIban(testAccount, testAccount.getIban());
        assertNotNull(testAccount);
    }

}

