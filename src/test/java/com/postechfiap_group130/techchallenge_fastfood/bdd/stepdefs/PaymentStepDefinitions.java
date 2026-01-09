package com.postechfiap_group130.techchallenge_fastfood.bdd.stepdefs;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdatePaymentRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PaymentStepDefinitions {
    
    @LocalServerPort
    private int port;

    @Autowired
    private Environment environment;

    private Response response;
    private String paymentEndpoint;
    private PaymentDto payment;

    @Before
    public void setUp() {
        // Configure RestAssured
        RestAssured.port = port;
        RestAssured.baseURI = "http://localhost";
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        
        paymentEndpoint = "/mock/payments";
        
        System.out.println("Test client configured with base URL: " + 
            RestAssured.baseURI + ":" + port + paymentEndpoint);
    }

    @Given("the payment service is running")
    public void thePaymentServiceIsRunning() {
        // The service is started by the Spring context
    }

    @When("I process a payment for order #{int} with amount {double}")
    public void iProcessAPaymentForOrderWithAmount(Integer orderId, Double amount) {
        try {
            Map<String, Object> paymentRequest = new HashMap<>();
            paymentRequest.put("orderId", orderId);
            paymentRequest.put("amount", amount);
            
            System.out.println("Sending payment request to: " + paymentEndpoint);
            System.out.println("Request body: " + paymentRequest);
            
            response = given()
                    .contentType(ContentType.JSON)
                    .body(paymentRequest)
                .when()
                    .post(paymentEndpoint);;
            
            System.out.println("Response status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody().asString());

            if (response.getStatusCode() == 200) {
                payment = response.as(PaymentDto.class);
                System.out.println("Payment processed: " + payment);
            }
        } catch (Exception e) {
            System.err.println("Error processing payment: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Then("the payment should be approved")
    public void thePaymentShouldBeApproved() {
        response.then()
                .statusCode(200)
                .body("status", equalTo("APPROVED"));
    }

    @Then("the payment should be declined")
    public void thePaymentShouldBeDeclined() {
        response.then()
                .statusCode(200)
                .body("status", equalTo("REJECTED"));
    }

    @And("the payment status should be {string}")
    public void thePaymentStatusShouldBe(String status) {
        response.then()
                .statusCode(200)
                .body("status", equalTo(status));
    }

    @Given("a payment with ID {int} exists with status {string}")
    public void aPaymentWithIDExistsWithStatus(String paymentId, String status) {
        // This is a setup step - in a real test, you would create a test payment with the given ID and status
        this.payment = new PaymentDto(paymentId, 1L, new BigDecimal("100.00"),
                                    PaymentStatusEnum.valueOf(status));
    }

    @When("I check the status of payment #{int}")
    public void iCheckTheStatusOfPayment(int paymentId) {
        try {
            System.out.println("Checking status of payment: " + paymentId);
            
            response = given()
                    .contentType(ContentType.JSON)
                .when()
                    .get(paymentEndpoint + "/" + paymentId);
            
            System.out.println("Response status: " + response.getStatusCode());
            System.out.println("Response body: " + response.getBody().asString());
            
            if (response.getStatusCode() == 200) {
                payment = response.as(PaymentDto.class);
            }
        } catch (Exception e) {
            System.err.println("Error checking payment status: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @When("I update payment #{int} status to {string}")
    public void iUpdatePaymentStatusTo(int paymentId, String newStatus) {
        try {
            UpdatePaymentRequestDto updateRequest = new UpdatePaymentRequestDto(newStatus);
            updateRequest.setStatus(newStatus);
            
            System.out.println("Updating payment " + paymentId + " to status: " + newStatus);
            
            response = given()
                    .contentType(ContentType.JSON)
                    .body(updateRequest)
                .when()
                    .put(paymentEndpoint + "/" + paymentId);
            
            System.out.println("Update response status: " + response.getStatusCode());
            System.out.println("Update response body: " + response.getBody().asString());
            
            if (response.getStatusCode() == 200) {
                payment = response.as(PaymentDto.class);
            }
        } catch (Exception e) {
            System.err.println("Error updating payment status: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }
}
