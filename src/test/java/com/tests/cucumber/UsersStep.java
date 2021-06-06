package com.tests.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class UsersStep {
    @Given("the user logged in is an employee")
    public void theUserLoggedInIsAnEmployee() {
    }

    @When("visiting the user endpoint")
    public void visitingTheUserEndpoint() {
    }

    @Then("the employee should see all users")
    public void theEmployeeShouldSeeAllUsers() {
    }

    @When("visiting the add user endpoint")
    public void visitingTheAddUserEndpoint() {
    }

    @And("user is set in the request body")
    public void userIsSetInTheRequestBody() {
    }

    @Then("user should be created")
    public void userShouldBeCreated() {
    }

    @And("the user id is set in the url")
    public void theUserIdIsSetInTheUrl() {
    }

    @Then("the employee should see the user matching the id")
    public void theEmployeeShouldSeeTheUserMatchingTheId() {
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
