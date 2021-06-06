Feature: Users


  Scenario: Get Users (employee)
    Given the user logged in is an employee
    When visiting the user endpoint
    And the http verb is GET
    And the offset and limit are set
    Then the employee should see all users

  Scenario: Create User
    Given the user logged in is an employee
    When visiting the add user endpoint
    And user is set in the request body
    Then user should be created

  Scenario: Get User by ID (employee)
    Given the user logged in is an employee
    When visiting the user endpoint
    And the user id is set in the url
    Then the employee should see the user matching the id

  Scenario: Update User (employee)
    Given the user logged in is an employee
    When visiting the update user endpoint
    And the user id is set in the url
    And the new user information is in the request body
    Then the user should be updated

  Scenario: Delete User
    Given the user logged in is an employee
    When visiting the delete user endpoint
    And the user id is set in the url
    Then the user should be deleted

  Scenario: Get User by ID (customer)
    Given the user logged in is a customer
    When visiting the user endpoint
    And the user id is set in the url
    And the account belongs to the user that is logged in
    Then the customer should see the user information matching the id

  Scenario: Update User (customer)
    Given the user logged in is a customer
    When visiting the update user endpoint
    And the user id is set in the url
    And the user information belongs to the user that is logged in
    And the new user information is in the request body
    Then the user should be updated
