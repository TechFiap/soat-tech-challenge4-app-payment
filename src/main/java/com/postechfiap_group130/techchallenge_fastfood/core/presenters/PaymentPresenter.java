package com.postechfiap_group130.techchallenge_fastfood.core.presenters;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Payment;

public class PaymentPresenter {

    public static PaymentDto toDto(Payment payment) {
        return new PaymentDto(
            payment.getId(),
            payment.getOrderId(),
            payment.getAmount(),
            payment.getStatus()
        );
    }
} 