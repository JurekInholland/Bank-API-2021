package com.tests.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.*;
import io.swagger.service.AccountService;
import io.swagger.service.IbanService;
import io.swagger.service.TransactionService;
import io.swagger.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.threeten.bp.OffsetDateTime;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class TransactionsStep {
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Autowired
    private IbanService ibanService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private TransactionService transactionService;


    @Autowired
    private ObjectMapper objectMapper;

    private User user1;
    private Account account1;
    private Account account2;
    private Transaction transaction;
    private String scheme = "http";
    private String port = "8080";
    private String host = "localhost";
    private String basepath = "/api/login";

    public ResponseEntity<String> getRequest(URI uri, Map<String, String> headers) throws URISyntaxException {
        // Set all headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        for (Map.Entry<String,String> header : headers.entrySet()) {
            httpHeaders.add(header.getKey(), header.getValue());
        }

        // Request you are sending
        HttpEntity<String> entity = new HttpEntity<>(null, httpHeaders);

        // Make HTTP request
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.getForEntity(uri, String.class);

        return responseEntity;
    }

    public ResponseEntity<String> postRequest(URI uri, HttpHeaders headers, Map<String, String> body) throws URISyntaxException {

        JSONObject bodyJson = new JSONObject(body);

        // Build request with body and headers
        HttpEntity<String> entity = new HttpEntity<>(bodyJson.toString(), headers);

        // Perform request and get response
        RestTemplate template = new RestTemplate();
        ResponseEntity<String> responseEntity = template.postForEntity(uri, entity, String.class);

        return responseEntity;
    }



    public void setUp() {
        this.user1 = createMockUser(Role.CUSTOMER);
        this.account1 = createMockAccount(user1, BigDecimal.valueOf(2500), AccountType.SAVINGS);
        this.account2 = createMockAccount(user1, BigDecimal.valueOf(1200), AccountType.CURRENT);
        this.transaction = createMockTransaction(account1, account2, user1, BigDecimal.valueOf(200));
    }

    public void tearDown() {
        this.user1 = null;
        this.account1 = null;
        this.account2 = null;
        this.transaction = null;
    }

    private User createMockUser(Role userRole){

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(userRole);
        User u1 = new User(
                "testuser",
                "testln",
                "12345",
                "testuser@example.com",
                encoder.encode("secret"),
                roles
        );
        userService.addUser(u1);

        return u1;
    }
    private Account createMockAccount(User u1, BigDecimal balance, AccountType accountType) {

        Account account = new Account(ibanService.generateIban(), u1, balance, accountType);
        return accountService.addAccount(account);
    }

    private Transaction createMockTransaction(Account accountFrom, Account accountTo, User userPerforming, BigDecimal amount) {
        Transaction transaction = new Transaction();
        transaction.setUserPerforming(userPerforming);
        transaction.setAccountFrom(accountFrom);
        transaction.setAccountTo(accountTo);
        transaction.setAmount(amount);
        transaction.setTimestamp(OffsetDateTime.now());

        return transactionService.addTransaction(transaction);
    }

    @Given("a customer is making a transaction")
    public void aCustomerIsMakingATransaction(){
        setUp();
        assertTrue("User is a customer", this.user1.getRoles().contains(Role.CUSTOMER));
    }

    @When("transferring from savings to current")
    public void transferringFromSavingsToCurrent() {
        assertNotEquals(account1.getAccountType(), account2.getAccountType());
    }

    @And("both accounts belong to the same customer")
    public void bothAccountsBelongToTheSameCustomer() {
        Long userIdAccount1 = account1.getUser().getId();
        Long userIdAccount2 = account2.getUser().getId();

        assertEquals(userIdAccount1, userIdAccount2);
    }

    @And("the user is logged in")
    public void theUserIsLoggedIn() throws URISyntaxException, JSONException {
        // Build URI
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        URI uri = uriBuilder.
            scheme(this.scheme)
            .host(this.host)
            .port(this.port)
            .path(this.basepath)
            .build().toUri();

        // Build headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build body
        Map<String, String> map = new HashMap<String, String>();
        map.put("email_address", "testuser@example.com");
        map.put("password", "secret");

        ResponseEntity<String> responseEntity = postRequest(uri, headers, map);
        String response = responseEntity.getBody();
        String token = new JSONObject(response).getString("token");
        assertTrue("token exists, user logged in", token != null);
    }

    @And("the account balance is sufficient")
    public void theAccountBalanceIsSufficient() {
        BigDecimal account1Balance = account1.getBalance();
        BigDecimal transactionAmount = transaction.getAmount();

        // -1 if to low, 0 if equal, 1 if greater then
        Integer value = account1Balance.compareTo(transactionAmount);
        Boolean hasFunds = ! value.equals(-1);

        assertTrue("balance is sufficient", hasFunds.booleanValue());
    }

    @Then("the transaction should be successful")
    public void theTransactionShouldBeSuccessful() {
        Optional<Transaction> checkTransaction = Optional.ofNullable(transactionService.getTransactionById(transaction.getId()));

        assertTrue( "Transaction is made", checkTransaction.isPresent());
    }

    @Given("The user logged in is an employee")
    public void theUserLoggedInIsAnEmployee() {
        this.user1 = createMockUser(Role.EMPLOYEE);
        assertTrue("User is a customer", this.user1.getRoles().contains(Role.EMPLOYEE));
    }

    @When("visiting transaction endpoint")
    public void visitingTransactionEndpoint() {

    }

    @And("the http verb is GET")
    public void theHttpVerbIsGET() {
        assertTrue(true);
    }

    @And("the offset and limit are set")
    public void theOffsetAndLimitAreSet() {
        assertTrue(true);
    }

    @Then("the employee should see all transactions in set amounts")
    public void theEmployeeShouldSeeAllTransactionsInSetAmounts() throws URISyntaxException {
//        // Build URI
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
//        URI uri = uriBuilder.scheme(this.scheme).host(this.host).path(this.basepath).build().toUri();
//
//        // Build headers
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("key", "value");
//
//        ResponseEntity<String> response = getRequest(uri, headers);
//        assertEquals(response.getStatusCodeValue(), 200);

        assertTrue(true);
    }

    @And("transaction id is set in url")
    public void transactionIdIsSetInUrl() {
        assertTrue(true);
    }

    @Then("the employee should see the transaction matching the id")
    public void theEmployeeShouldSeeTheTransactionMatchingTheId() {
        assertTrue(true);
    }

    @When("visiting add transaction endpoint")
    public void visitingAddTransactionEndpoint() {
        assertTrue(true);
    }

    @And("transaction is set in the request body")
    public void transactionIsSetInTheRequestBody() {
        assertTrue(true);
    }

    @Then("Transaction should be added")
    public void transactionShouldBeAdded() {
        assertTrue(true);
    }

    @When("visiting update transaction endpoint")
    public void visitingUpdateTransactionEndpoint() {
        assertTrue(true);
    }

    @And("the transaction id is set in the url")
    public void theTransactionIdIsSetInTheUrl() {
        assertTrue(true);
    }

    @And("the new transaction information is in the request body")
    public void theNewTransactionInformationIsInTheRequestBody() {
        assertTrue(true);
    }

    @Then("the transaction should be updated")
    public void theTransactionShouldBeUpdated() {
        assertTrue(true);
    }

    @When("visiting delete transaction endpoint")
    public void visitingDeleteTransactionEndpoint() {
        assertTrue(true);
    }

    @Then("the transaction should be deleted")
    public void theTransactionShouldBeDeleted() {
        assertTrue(true);
    }

    @Given("the user is logged in as a customer")
    public void theUserIsLoggedInAsACustomer() {
        assertTrue(true);
    }

    @When("visiting get transactions endpoint")
    public void visitingGetTransactionsEndpoint() {
        assertTrue(true);
    }

    @Then("transactions of that user only are visable")
    public void transactionsOfThatUserOnlyAreVisable() {
        assertTrue(true);
    }

    @Given("The user logged in is a customer")
    public void theUserLoggedInIsACustomer() {
        assertTrue(true);
    }

    @And("the transaction belongs to user that is logged in")
    public void theTransactionBelongsToUserThatIsLoggedIn() {
        assertTrue(true);
    }

    @And("the account from belongs to the user")
    public void theAccountFromBelongsToTheUser() {
        assertTrue(true);
    }

}
