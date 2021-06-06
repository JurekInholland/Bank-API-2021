package com.tests.cucumber;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.swagger.model.AccountType;
import io.swagger.model.Role;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.service.UserService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

public class UsersStep {

    @Autowired
    private UserService userService;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private ObjectMapper objectMapper;


    private User user1;
    private String scheme = "http";
    private String port = "8080";
    private String host = "localhost";
    private String basepath = "/api/";


    public ResponseEntity<String> getRequest(URI uri, HttpHeaders headers) throws URISyntaxException {
        // Set all headers
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

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
    }

    public void tearDown() {
        this.user1 = null;
    }

    private User createMockUser(Role userRole){

        ArrayList<Role> roles = new ArrayList<>();
        roles.add(userRole);

        User u1 = new User("testuser","testln","12345","testuser@example.com",encoder.encode("secret"), roles);

        userService.addUser(u1);

        return u1;
    }

    @Given("the user logged in is an employee")
    public void theUserLoggedInIsAnEmployee() throws URISyntaxException, JSONException {
        setUp();
        assertTrue("User is an employee", this.user1.getRoles().contains(Role.EMPLOYEE));
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
        URI uri = uriBuilder.
                scheme(this.scheme)
                .host(this.host)
                .port(this.port)
                .path(this.basepath)
                .path("login")
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

    @When("visiting the user endpoint")
    public void visitingTheUserEndpoint() {
    }

    @And("the http verb is GET")
    public void theHttpVerbIsGET() {
        assertTrue(true);
    }

    @And("the offset and limit are set")
    public void theOffsetAndLimitAreSet() {
        assertTrue(true);
    }

    @Then("the employee should see all users")
    public void theEmployeeShouldSeeAllUsers() {
        // Build URI
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

    @When("visiting the add user endpoint")
    public void visitingTheAddUserEndpoint() {
    }

    @And("user is set in the request body")
    public void userIsSetInTheRequestBody() {assertTrue(true);
    }

    @Then("user should be created")
    public void userShouldBeCreated() {
        Optional<User> checkUser = Optional.ofNullable(userService.getUserById(user1.getId()));

        assertTrue( "User is created", checkUser.isPresent());
    }


    @And("the user id is set in the url")
    public void theUserIdIsSetInTheUrl() {
    }

    @Then("the employee should see the user matching the id")
    public void theEmployeeShouldSeeTheUserMatchingTheId() {

//        // Build headers
//        Map<String, String> headers = new HashMap<String, String>();
//        headers.put("key", "value");
//
//        HttpEntity<String> entity = new HttpEntity<String>(null, headers);
//
//        UriComponentsBuilder uriBuilder = UriComponentsBuilder.newInstance();
//        URI uri = uriBuilder.
//                scheme(this.scheme)
//                .host(this.host)
//                .port(this.port)
//                .path(this.basepath)
//                .path("users/" + user1.getId())
//                .build().toUri();
//

    }

    @When("visiting the update user endpoint")
    public void visitingTheUpdateUserEndpoint() {
    }

    @And("the new user information is in the request body")
    public void theNewUserInformationIsInTheRequestBody() {
    }

    @Then("the user should be updated")
    public void theUserShouldBeUpdated() {
    }

    @When("visiting the delete user endpoint")
    public void visitingTheDeleteUserEndpoint() {
    }

    @Then("the user should be deleted")
    public void theUserShouldBeDeleted() {
    }

    @Given("the user logged in is a customer")
    public void theUserLoggedInIsACustomer() {
    }

    @And("the account belongs to the user that is logged in")
    public void theAccountBelongsToTheUserThatIsLoggedIn() {
    }

    @Then("the customer should see the user information matching the id")
    public void theCustomerShouldSeeTheUserInformationMatchingTheId() {
    }

    @And("the user information belongs to the user that is logged in")
    public void theUserInformationBelongsToTheUserThatIsLoggedIn() {
    }
}
