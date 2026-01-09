package com.postechfiap_group130.techchallenge_fastfood.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Payment {
    private String id;
    private Long orderId;
    private BigDecimal amount;
    private PaymentStatusEnum status;

    public Payment(Long orderId, BigDecimal amount) {
        if (orderId == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        this.orderId = orderId;
        this.amount = amount;
        this.status = PaymentStatusEnum.PENDING;
    }

    public Payment(String id, PaymentStatusEnum status) {
        if (id == null) {
            throw new IllegalArgumentException("Payment ID cannot be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("Payment status cannot be null");
        }
        this.id = id;
        this.status = status;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setStatus(PaymentStatusEnum status) {
        this.status = status;
    }
}