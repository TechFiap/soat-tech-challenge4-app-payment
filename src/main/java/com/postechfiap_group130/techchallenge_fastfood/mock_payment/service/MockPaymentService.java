package com.postechfiap_group130.techchallenge_fastfood.mock_payment.service;

import com.postechfiap_group130.techchallenge_fastfood.mock_payment.config.MockPaymentConfig;
import com.postechfiap_group130.techchallenge_fastfood.mock_payment.model.MockPaymentRequest;
import com.postechfiap_group130.techchallenge_fastfood.mock_payment.model.MockPaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
@Service
@RequiredArgsConstructor
public class MockPaymentService {

    private final RestTemplate restTemplate;
    private final MockPaymentConfig config;
    private final AtomicInteger requestCounter = new AtomicInteger(0);

    public MockPaymentResponse processPayment(MockPaymentRequest request) {
        int count = requestCounter.incrementAndGet();
        boolean isApproved = count % 3 != 0;
        
        String status = isApproved ? "APPROVED" : "REJECTED";

        MockPaymentResponse response = new MockPaymentResponse(
                request.getPaymentId(),
                status
        );

        sendToWebhook(response);

        return response;
    }

    private void sendToWebhook(MockPaymentResponse response) {
        try {
            String webhookUrl = config.getWebhookUrl();
            ResponseEntity<String> webhookResponse = restTemplate.postForEntity(
                    webhookUrl,
                    response,
                    String.class
            );
            log.info("Webhook response: {} - {}",
                    webhookResponse.getStatusCode(),
                    webhookResponse.getBody());
        } catch (Exception e) {
            log.error("Failed to send payment response to webhook", e);
        }
    }
}
