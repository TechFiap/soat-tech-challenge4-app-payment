package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.Payment;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.PaymentGateway;

public class CheckPaymentStatusUseCase {

    private final PaymentGateway paymentGateway;

    public CheckPaymentStatusUseCase(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public Payment execute(String paymentId) {
        if (paymentId == null) {
            throw new IllegalArgumentException("Payment ID cannot be null");
        }
        
        return paymentGateway.checkPaymentStatus(paymentId);
    }
} 