package com.postechfiap_group130.techchallenge_fastfood.mock_payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class MockPaymentResponseTest {

    @Test
    void shouldCreateEmptyConstructor() {
        MockPaymentResponse response = new MockPaymentResponse();
        assertNotNull(response);
    }

    @Test
    void shouldCreateAllArgsConstructor() {
        MockPaymentResponse response = new MockPaymentResponse("PAY123", "APPROVED");

        assertEquals("PAY123", response.getPaymentId());
        assertEquals("APPROVED", response.getStatus());
    }

    @Test
    void shouldSetAndGetFields() {
        MockPaymentResponse response = new MockPaymentResponse();

        response.setPaymentId("ID001");
        response.setStatus("REJECTED");

        assertEquals("ID001", response.getPaymentId());
        assertEquals("REJECTED", response.getStatus());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        MockPaymentResponse r1 = new MockPaymentResponse("A", "APPROVED");
        MockPaymentResponse r2 = new MockPaymentResponse("A", "APPROVED");
        MockPaymentResponse r3 = new MockPaymentResponse("B", "REJECTED");

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        assertNotEquals(r1, r3);
        assertNotEquals(r1.hashCode(), r3.hashCode());
    }

    @Test
    void shouldNotBeEqualToDifferentObjectType() {
        MockPaymentResponse response = new MockPaymentResponse("X", "APPROVED");
        assertNotEquals(response, "string");
    }

    @Test
    void shouldNotBeEqualWhenNull() {
        MockPaymentResponse response = new MockPaymentResponse("X", "APPROVED");
        assertNotEquals(response, null);
    }

    @Test
    void shouldTestToString() {
        MockPaymentResponse response = new MockPaymentResponse("PAY999", "APPROVED");

        String result = response.toString();

        assertNotNull(result);
        assertTrue(result.contains("PAY999"));
        assertTrue(result.contains("APPROVED"));
    }

    @Test
    void shouldHaveCorrectJsonPropertyAnnotations() throws Exception {
        Field paymentIdField = MockPaymentResponse.class.getDeclaredField("paymentId");
        Field statusField = MockPaymentResponse.class.getDeclaredField("status");

        JsonProperty paymentIdAnnotation = paymentIdField.getAnnotation(JsonProperty.class);
        JsonProperty statusAnnotation = statusField.getAnnotation(JsonProperty.class);

        assertNotNull(paymentIdAnnotation);
        assertNotNull(statusAnnotation);

        assertEquals("payment_id", paymentIdAnnotation.value());
        assertEquals("status", statusAnnotation.value());
    }
}