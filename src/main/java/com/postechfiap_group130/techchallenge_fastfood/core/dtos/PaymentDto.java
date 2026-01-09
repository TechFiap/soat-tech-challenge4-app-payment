package com.postechfiap_group130.techchallenge_fastfood.core.dtos;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;

import java.math.BigDecimal;

public record PaymentDto(
    String id,
    Long orderId,
    BigDecimal amount,
    PaymentStatusEnum status
) {
    public PaymentDto(Long orderId, BigDecimal amount, PaymentStatusEnum status) {
        this(null, orderId, amount, status);
    }
}