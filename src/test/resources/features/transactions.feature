Feature: Transactions


  Scenario: SavingsTransaction
    Given a customer is making a transaction
    When transferring from savings to current
    And both accounts belong to the same customer
    And the user is logged in
    And the account balance is sufficient
    Then the transaction should be successful

  Scenario: Get Transactions (employee)
    Given The user logged in is an employee
    When visiting transaction endpoint
    And the http verb is GET
    And the offset and limit are set
    Then the employee should see all transactions in set amounts

  Scenario: Get Transaction by Id (employee)
    Given The user logged in is an employee
    When visiting transaction endpoint
    And transaction id is set in url
    Then the employee should see the transaction matching the id

  Scenario: Add Transaction (employee)
    Given the user logged in is an employee
    When visiting add transaction endpoint
    And transaction is set in the request body
    Then Transaction should be added

  Scenario: Update Transaction (employee)
    Given the user logged in is an employee
    When visiting update transaction endpoint
    And the transaction id is set in the url
    And the new transaction information is in the request body
    Then the transaction should be updated

  Scenario: Delete Transaction (employee)
    Given the user logged in is an employee
    When visiting delete transaction endpoint
    And the transaction id is set in the url
    Then the transaction should be deleted

  Scenario: Get Transactions (customer)
    Given the user is logged in as a customer
    When visiting get transactions endpoint
    Then transactions of that user only are visable

  Scenario: Get Transaction by Id (customer)
    Given The user logged in is a customer
    When visiting transaction endpoint
    And transaction id is set in url
    And the transaction belongs to user that is logged in
    Then the employee should see the transaction matching the id

  Scenario: Add Transaction (customer)
    Given the user logged in is a customer
    When visiting add transaction endpoint
    And transaction is set in the request body
    And the account from belongs to the user
    Then Transaction should be added

  Scenario: Update Transaction (customer)
    Given the user logged in is a customer
    When visiting update transaction endpoint
    And the transaction id is set in the url
    And the transaction belongs to user that is logged in
    And the new transaction information is in the request body
    Then the transaction should be updated

  Scenario: Delete Transaction (customer)
    Given the user is logged in as a customer
    When visiting delete transaction endpoint
    And the transaction id is set in the url
    And the transaction belongs to user that is logged in
    Then the transaction should be deleted
