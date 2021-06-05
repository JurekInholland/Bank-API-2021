Feature: Accounts

  Scenario: Get accounts (Employee)
    Given the user logged in is an employee
    When the user visits accounts endpoint
    And the http verb is GET
    And the offset and limit are set
    Then the api will return a list of all accounts

  Scenario: Get Account by IBAN (employee)
    Given the user logged in is an employee
    When visiting accounts endpoint
    And account iban is set in the url
    Then the employee should see the account matching the iban

  Scenario: Create Account (employee)
    Given the user logged in is an employee
    When visiting the add account endpoint
    And account is set in the request body
    Then Account should be created

  Scenario: Update Account (employee)
    Given the user logged in is an employee
    When visiting the update account endpoint
    And the account iban is set in the url
    And the new account information is in the request body
    Then the account should be updated

  Scenario: Delete Account
    Given the user logged in is an employee
    When visiting the delete account endpoint
    And the account iban is set in the url
    Then the account should be deleted

  Scenario: Create Account (customer)
    Given the user is logged in as a customer
    When visiting the add account endpoint
    And account is set in the request body
    Then account should be created

  Scenario: Get Accounts (customer)
    Given the user is logged in as a customer
    When visiting GET accounts endpoint
    Then the user will see public accounts information

  Scenario: Get Account by IBAN (customer)
    Given the user is logged in as a customer
    When visiting the account endpoint
    And account iban is set in the url
    Then the customer should see only the public information matching the iban

  Scenario: Update Account (customer)
    Given the user is logged in as a customer
    When visiting the update account endpoint
    And the account iban is set in the url
    And the account belongs to the user that is logged in
    And the new account information is in the request body
    Then the account should be updated
