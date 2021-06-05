package com.tests.cucumber;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.junit.Assert.*;

public class BeerCan {
    @Given("I have <opening balance> beer cans")
    public void iHaveOpeningBalanceBeerCans() {
        assertTrue(true);
    }

    @And("I have drunk <processed> beer cans")
    public void iHaveDrunkProcessedBeerCans() {
        assertTrue(true);
    }

    @When("I go to my fridge")
    public void iGoToMyFridge() {
        assertTrue(true);
    }

    @Then("I should have <in stock> beer cans")
    public void iShouldHaveInStockBeerCans() {
        assertTrue(true);
    }
}
