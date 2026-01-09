package com.postechfiap_group130.techchallenge_fastfood.mock_payment.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MockPaymentResponse {
    @JsonProperty("payment_id") private String paymentId;
    @JsonProperty("status") private String status; // APPROVED or REJECTED
}
