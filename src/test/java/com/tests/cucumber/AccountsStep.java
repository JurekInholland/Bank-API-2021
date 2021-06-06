package com.tests.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class AccountsStep {
    @When("the user visits accounts endpoint")
    public void theUserVisitsAccountsEndpoint() {
    }

    @Then("the api will return a list of all accounts")
    public void theApiWillReturnAListOfAllAccounts() {
    }

    @When("visiting accounts endpoint")
    public void visitingAccountsEndpoint() {
    }

    @And("account iban is set in the url")
    public void accountIbanIsSetInTheUrl() {
    }

    @Then("the employee should see the account matching the iban")
    public void theEmployeeShouldSeeTheAccountMatchingTheIban() {
    }

    @When("visiting the add account endpoint")
    public void visitingTheAddAccountEndpoint() {
    }

    @And("account is set in the request body")
    public void accountIsSetInTheRequestBody() {
    }

//    @Then("Account should be created")
//    public void accountShouldBeCreated() {
//    }

    @When("visiting the update account endpoint")
    public void visitingTheUpdateAccountEndpoint() {
    }

    @And("the account iban is set in the url")
    public void theAccountIbanIsSetInTheUrl() {
    }

    @And("the new account information is in the request body")
    public void theNewAccountInformationIsInTheRequestBody() {
    }

    @Then("the account should be updated")
    public void theAccountShouldBeUpdated() {
    }

    @When("visiting the delete account endpoint")
    public void visitingTheDeleteAccountEndpoint() {
    }

    @Then("the account should be deleted")
    public void theAccountShouldBeDeleted() {
    }

    @Then("account should be created")
    public void accountShouldBeCreated() {
    }

    @When("visiting GET accounts endpoint")
    public void visitingGETAccountsEndpoint() {
    }

    @Then("the user will see public accounts information")
    public void theUserWillSeePublicAccountsInformation() {
    }

    @When("visiting the account endpoint")
    public void visitingTheAccountEndpoint() {
    }

    @Then("the customer should see only the public information matching the iban")
    public void theCustomerShouldSeeOnlyThePublicInformationMatchingTheIban() {
    }
}
