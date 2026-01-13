package com.postechfiap_group130.techchallenge_fastfood.mock_payment.service;

import com.postechfiap_group130.techchallenge_fastfood.mock_payment.config.MockPaymentConfig;
import com.postechfiap_group130.techchallenge_fastfood.mock_payment.model.MockPaymentRequest;
import com.postechfiap_group130.techchallenge_fastfood.mock_payment.model.MockPaymentResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MockPaymentServiceTest {

    private RestTemplate restTemplate;
    private MockPaymentConfig config;
    private MockPaymentService service;

    @BeforeEach
    void setup() {
        restTemplate = mock(RestTemplate.class);
        config = mock(MockPaymentConfig.class);
        service = new MockPaymentService(restTemplate, config);
    }

    @Test
    void shouldApprovePaymentWhenNotThirdRequest() {
        when(config.getWebhookUrl()).thenReturn("http://test-webhook");

        MockPaymentRequest request = new MockPaymentRequest();
        request.setPaymentId("PAY123");

        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("OK", HttpStatus.OK));

        MockPaymentResponse response = service.processPayment(request);

        assertEquals("APPROVED", response.getStatus());
        assertEquals("PAY123", response.getPaymentId());
    }

    @Test
    void shouldRejectPaymentOnThirdRequest() {
        when(config.getWebhookUrl()).thenReturn("http://test-webhook");

        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("OK", HttpStatus.OK));

        MockPaymentRequest request = new MockPaymentRequest();
        request.setPaymentId("PAY999");

        // First call → APPROVED
        service.processPayment(request);
        // Second call → APPROVED
        service.processPayment(request);
        // Third call → REJECTED
        MockPaymentResponse response = service.processPayment(request);

        assertEquals("REJECTED", response.getStatus());
    }

    @Test
    void shouldSendWebhookWithCorrectPayload() {
        when(config.getWebhookUrl()).thenReturn("http://webhook-url");

        MockPaymentRequest request = new MockPaymentRequest();
        request.setPaymentId("ABC");

        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenReturn(new ResponseEntity<>("OK", HttpStatus.OK));

        service.processPayment(request);

        ArgumentCaptor<MockPaymentResponse> captor = ArgumentCaptor.forClass(MockPaymentResponse.class);

        verify(restTemplate, times(1))
                .postForEntity(eq("http://webhook-url"), captor.capture(), eq(String.class));

        MockPaymentResponse sent = captor.getValue();

        assertEquals("ABC", sent.getPaymentId());
        assertNotNull(sent.getStatus());
    }

    @Test
    void shouldHandleWebhookExceptionGracefully() {
        when(config.getWebhookUrl()).thenReturn("http://webhook-url");

        MockPaymentRequest request = new MockPaymentRequest();
        request.setPaymentId("ERR");

        when(restTemplate.postForEntity(anyString(), any(), eq(String.class)))
                .thenThrow(new RuntimeException("Webhook failure"));

        MockPaymentResponse response = service.processPayment(request);

        assertNotNull(response);
        assertEquals("ERR", response.getPaymentId());

        verify(restTemplate, times(1))
                .postForEntity(anyString(), any(), eq(String.class));
    }
}
