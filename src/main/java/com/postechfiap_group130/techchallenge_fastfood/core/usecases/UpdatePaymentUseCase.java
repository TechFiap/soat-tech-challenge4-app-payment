package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.PaymentRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdatePaymentRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Payment;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.PaymentGateway;

import java.math.BigDecimal;

public class UpdatePaymentUseCase {

    private final PaymentGateway paymentGateway;

    public UpdatePaymentUseCase(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public Payment execute(String paymentId, UpdatePaymentRequestDto updatePaymentRequestDto) {
        Payment payment = new Payment(paymentId, PaymentStatusEnum.valueOf(updatePaymentRequestDto.getStatus()));
        return paymentGateway.updatePaymentStatus(payment);
    }
} 