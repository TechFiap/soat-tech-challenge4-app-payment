package com.postechfiap_group130.techchallenge_fastfood.core.gateways;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Payment;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.IPaymentGateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

public class PaymentGateway implements IPaymentGateway {

    private final DataSource dataSource;

    public PaymentGateway(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Payment save(Payment payment) {
        PaymentDto paymentDto = new PaymentDto(
                null,
                payment.getOrderId(),
                payment.getAmount(),
                payment.getStatus()
        );

        PaymentDto savedPayment = dataSource.savePayment(paymentDto);
        payment.setId(savedPayment.id());
        
        return payment;
    }

    @Override
    public Optional<Payment> findById(String paymentId) {
        return dataSource.findPaymentById(paymentId)
                .map(this::mapToPayment);
    }

    @Override
    public Payment checkPaymentStatus(String paymentId) {
        Optional<Payment> payment = findById(paymentId);
        return payment.orElse(null);
    }

    @Override
    public Payment updatePaymentStatus(Payment payment) {
        Payment findPayment = findById(payment.getId()).orElse(null);
        if (findPayment != null) {
            PaymentDto paymentDto = new PaymentDto(
                payment.getId(),
                findPayment.getOrderId(),
                findPayment.getAmount(),
                payment.getStatus()
            );

            PaymentDto updatedPayment = dataSource.updatePaymentStatus(paymentDto);
            return mapToPayment(updatedPayment);
        }
        return null;
    }
    
    private Payment mapToPayment(PaymentDto paymentDto) {
        return new Payment(
            paymentDto.id(),
            paymentDto.orderId(),
            paymentDto.amount(),
            paymentDto.status()
        );
    }
}