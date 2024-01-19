Feature: DTUPay account assignment
  # @Author: Jacob
  Scenario: Register DTUPay account
    Given an unregistered account exists
    When a "RegisterAccountRequested" event for a customer is received
    Then the "AccountCreated" event is sent
    And an account is created

  # @Author: Andreas
  Scenario: Account already exists
    Given an unregistered account exists
    And an account with the same id already exists
    When a "RegisterAccountRequested" event for a customer is received
    Then the "AccountAlreadyExists" event is sent with an empty response
    And an account is not created

  # @Author: Caroline
  Scenario: User mapped successfully
    Given an unregistered account exists
    Given a customer in DTUPay with id "0aa1c3ef-473d-44e3-8689-5ce60588dbf4"
    When a "GetDTUPayAccount" event for an account with id "0aa1c3ef-473d-44e3-8689-5ce60588dbf4" is received
    Then the "DTUPayAccountReturned" event is sent
    And the information of the account is returned

  # @Author: Jacob
  Scenario: User not found
    Given an unregistered account exists
    When a "GetDTUPayAccount" event for an account with id "013d0df0-d938-48df-8b41-5f1dfc484d4c" is received
    Then the "DTUPayAccountNotFound" event is sent with an empty response
    And the information of the account is empty