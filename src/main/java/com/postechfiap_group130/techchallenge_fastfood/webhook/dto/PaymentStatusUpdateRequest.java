package com.postechfiap_group130.techchallenge_fastfood.webhook.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record PaymentStatusUpdateRequest(
        @JsonProperty("status") String status
) { }
