Feature: Account service features
  Scenario: Successfully register user
    Given a customer with a bank account
    And the customer does not exist in DTUPay
    When the customer registers with DTUPay
    Then a customer with the same information as the bank customer exists in DTUPay

#  Scenario: User already exists
#    Given a customer with a bank account
#    And the customer already exists in DTUPay
#    When the customer registers with DTUPay
#    Then a message saying "Error: This user is already registered" is shown