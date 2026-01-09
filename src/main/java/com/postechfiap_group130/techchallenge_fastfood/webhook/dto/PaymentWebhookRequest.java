package com.postechfiap_group130.techchallenge_fastfood.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentWebhookRequest(
        @JsonProperty("payment_id") String paymentId,
        @JsonProperty("status") String status
) { }
