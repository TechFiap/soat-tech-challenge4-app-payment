package com.postechfiap_group130.techchallenge_fastfood.mock_payment.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MockPaymentRequestTest {

    @Test
    void shouldSetAndGetPaymentId() {
        MockPaymentRequest request = new MockPaymentRequest();
        request.setPaymentId("PAY123");

        assertEquals("PAY123", request.getPaymentId());
    }

    @Test
    void shouldTestEqualsAndHashCode() {
        MockPaymentRequest req1 = new MockPaymentRequest();
        req1.setPaymentId("ABC");

        MockPaymentRequest req2 = new MockPaymentRequest();
        req2.setPaymentId("ABC");

        MockPaymentRequest req3 = new MockPaymentRequest();
        req3.setPaymentId("XYZ");

        assertEquals(req1, req2);
        assertEquals(req1.hashCode(), req2.hashCode());

        assertNotEquals(req1, req3);
        assertNotEquals(req1.hashCode(), req3.hashCode());
    }

    @Test
    void shouldTestToString() {
        MockPaymentRequest request = new MockPaymentRequest();
        request.setPaymentId("TEST123");

        String result = request.toString();

        assertNotNull(result);
        assertTrue(result.contains("TEST123"));
    }

    @Test
    void shouldNotBeEqualToDifferentObjectType() {
        MockPaymentRequest request = new MockPaymentRequest();
        request.setPaymentId("ID1");

        assertNotEquals(request, "some string");
    }

    @Test
    void shouldNotBeEqualWhenNull() {
        MockPaymentRequest request = new MockPaymentRequest();
        request.setPaymentId("ID1");

        assertNotEquals(request, null);
    }
}