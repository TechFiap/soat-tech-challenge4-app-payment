Feature: Payment Processing
  As a payment system
  I want to process payments for orders
  So that customers can complete their purchases

  Background:
    Given the payment service is running

  Scenario: Process a successful payment
    When I process a payment for order #123 with amount 100.00
    Then the payment should be approved
    And the payment status should be "APPROVED"

  Scenario: Process a declined payment
    When I process a payment for order #456 with amount 1.00
    Then the payment should be declined
    And the payment status should be "REJECTED"

  Scenario: Check payment status
    Given a payment with ID 789 exists with status "PENDING"
    When I check the status of payment #789
    Then the payment status should be "PENDING"

  Scenario: Update payment status
    Given a payment with ID 101 exists with status "PENDING"
    When I update payment #101 status to "APPROVED"
    Then the payment status should be "APPROVED"
