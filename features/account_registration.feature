Feature: DTUPay account assignment
  Scenario: Register DTUPay account
    Given an unregistered account exists
    When a "RegisterAccountRequested" event for a customer is received
    Then the "AccountCreated" event is sent
    And an account is created

  Scenario: User mapped successfully
    Given an unregistered account exists
    Given a customer in DTUPay with id "0aa1c3ef-473d-44e3-8689-5ce60588dbf4"
    When a "GetDTUPayAccount" event for an account with id "0aa1c3ef-473d-44e3-8689-5ce60588dbf4" is received
    Then the "DTUPayAccountReturned" event is sent
    And the information of the account is returned