package com.postechfiap_group130.techchallenge_fastfood.mock_payment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MockPaymentConfig {

    @Value("${mock.payment.webhook.url:http://localhost:8080/webhook/payments}")
    private String webhookUrl;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public String getWebhookUrl() {
        return webhookUrl;
    }
}
