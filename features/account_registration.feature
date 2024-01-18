Feature: DTUPay account assignment
  Scenario: Register DTUPay account
    Given an account exists
    When a "RegisterAccountRequested" event for a customer is received
    Then the "AccountCreated" event is sent
    And an account is created

  Scenario: DTUPay account already exists
    Given an account exists
    And the account is already registered with DTUPay
    When a "RegisterAccountRequested" event for a customer is received
    Then the "AccountAlreadyExists" event is sent
    And no account is created

  Scenario: User mapped successfully
    Given an account exists
    Given a customer in DTUPay with id "0aa1c3ef-473d-44e3-8689-5ce60588dbf4"
    When a "GetAccountRequested" event for an account with id "0aa1c3ef-473d-44e3-8689-5ce60588dbf4" is received
    Then the "AccountReturned" event is sent
    And the information of the account is returned