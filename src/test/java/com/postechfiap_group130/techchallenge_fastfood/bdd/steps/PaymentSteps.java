package com.postechfiap_group130.techchallenge_fastfood.bdd.steps;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Payment;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import io.cucumber.java.en.*;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class PaymentSteps {

    private Long orderId;
    private BigDecimal amount;
    private Payment payment;
    private Exception capturedException;

    @Given("a valid order id {long} and amount {double}")
    public void a_valid_order_id_and_amount(Long orderId, Double amount) {
        this.orderId = orderId;
        this.amount = BigDecimal.valueOf(amount);
    }

    @When("the payment is created")
    public void the_payment_is_created() {
        try {
            payment = new Payment(orderId, amount);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("the payment status should be {string}")
    public void the_payment_status_should_be(String expectedStatus) {
        assertNotNull(payment);
        assertEquals(PaymentStatusEnum.valueOf(expectedStatus), payment.getStatus());
    }

    @When("the payment creation is attempted")
    public void the_payment_creation_is_attempted() {
        try {
            payment = new Payment(orderId, amount);
        } catch (Exception e) {
            capturedException = e;
        }
    }

    @Then("an exception should be thrown with message {string}")
    public void an_exception_should_be_thrown_with_message(String expectedMessage) {
        assertNotNull(capturedException);
        assertEquals(expectedMessage, capturedException.getMessage());
    }

    @Given("an existing payment with id {string} and status {string}")
    public void an_existing_payment_with_id_and_status(String id, String status) {
        payment = new Payment(id, PaymentStatusEnum.valueOf(status));
    }

    @When("the payment status is updated to {string}")
    public void the_payment_status_is_updated_to(String newStatus) {
        payment.setStatus(PaymentStatusEnum.valueOf(newStatus));
    }
}
