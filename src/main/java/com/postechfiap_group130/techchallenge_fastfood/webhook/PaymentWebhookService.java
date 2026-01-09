package com.postechfiap_group130.techchallenge_fastfood.webhook;

import com.postechfiap_group130.techchallenge_fastfood.webhook.dto.PaymentWebhookRequest;

public interface PaymentWebhookService {
    void processPaymentUpdate(PaymentWebhookRequest request);
}
