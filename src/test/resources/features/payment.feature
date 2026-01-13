Feature: Payment management
  As a system
  I want to validate and manage payments
  So that orders can be properly processed

  Scenario: Successfully create a valid payment
    Given a valid order id 100 and amount 50.00
    When the payment is created
    Then the payment status should be "PENDING"

  Scenario: Fail to create a payment with invalid amount
    Given a valid order id 100 and amount -10.00
    When the payment creation is attempted
    Then an exception should be thrown with message "Amount must be greater than zero"

  Scenario: Update payment status to APPROVED
    Given an existing payment with id "PAY123" and status "PENDING"
    When the payment status is updated to "APPROVED"
    Then the payment status should be "APPROVED"