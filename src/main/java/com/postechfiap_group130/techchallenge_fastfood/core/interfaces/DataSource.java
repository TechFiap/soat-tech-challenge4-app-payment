package com.postechfiap_group130.techchallenge_fastfood.core.interfaces;

import java.util.List;


import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import java.util.Optional;

public interface DataSource {

    PaymentDto savePayment(PaymentDto paymentDto);

    Optional<PaymentDto> findPaymentById(String paymentId);

    PaymentDto updatePaymentStatus(PaymentDto paymentDto);
}
