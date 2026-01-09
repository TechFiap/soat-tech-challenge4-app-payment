package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.PaymentRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Payment;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.PaymentGateway;

import java.math.BigDecimal;

public class CreatePaymentUseCase {

    private final PaymentGateway paymentGateway;

    public CreatePaymentUseCase(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public Payment execute(PaymentRequestDto paymentRequestDto) {
        if (paymentRequestDto.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null");
        }
        
        if (paymentRequestDto.getAmount() == null || paymentRequestDto.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be greater than zero");
        }

        Payment payment = new Payment(paymentRequestDto.getOrderId(), paymentRequestDto.getAmount());
        return paymentGateway.save(payment);
    }
} 