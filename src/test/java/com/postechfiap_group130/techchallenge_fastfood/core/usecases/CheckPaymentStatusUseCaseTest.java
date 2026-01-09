package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Payment;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.PaymentGateway;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckPaymentStatusUseCaseTest {

    private CheckPaymentStatusUseCase checkPaymentStatusUseCase;
    private PaymentGateway paymentGateway;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class);
        paymentGateway = new PaymentGateway(dataSource);
        checkPaymentStatusUseCase = new CheckPaymentStatusUseCase(paymentGateway);
    }

    @Test
    @DisplayName("Must return APROVADO for valid payments")
    void shouldReturnApprovedForValidPayments() {
        String paymentId = "ce578a5a-4f25-4d32-8ee8-cb17384600c4";
        when(dataSource.findPaymentById(paymentId)).thenReturn(Optional.of(
                new PaymentDto(paymentId, 1L, new BigDecimal("50.00"), PaymentStatusEnum.APPROVED)
        ));

        Payment payment = checkPaymentStatusUseCase.execute(paymentId);

        assertEquals(PaymentStatusEnum.APPROVED, payment.getStatus());
    }

    @Test
    @DisplayName("Must return REJECTED for invalid payments")
    void shouldReturnRejectedForInvalidPayments() {
        String paymentId = "ce578a5a-4f25-4d32-8ee8-cb17384600c4";
        when(dataSource.findPaymentById(paymentId)).thenReturn(Optional.of(
            new PaymentDto(paymentId, 1L, new BigDecimal("50.00"), PaymentStatusEnum.REJECTED)
        ));

        Payment payment = checkPaymentStatusUseCase.execute(paymentId);

        assertEquals(PaymentStatusEnum.REJECTED, payment.getStatus());
    }

    @Test
    @DisplayName("Must maintain consistency for same paymentId")
    void shouldMaintainConsistencyForSamePaymentId() {
        String paymentId = "ce578a5a-4f25-4d32-8ee8-cb17384600c4";
        when(dataSource.findPaymentById(paymentId)).thenReturn(Optional.of(
                new PaymentDto(paymentId, 1L, new BigDecimal("50.00"), PaymentStatusEnum.APPROVED)
        ));

        Payment payment1 = checkPaymentStatusUseCase.execute(paymentId);
        Payment payment2 = checkPaymentStatusUseCase.execute(paymentId);
        Payment payment3 = checkPaymentStatusUseCase.execute(paymentId);

        assertEquals(payment1.getStatus(), payment2.getStatus());
        assertEquals(payment2.getStatus(), payment3.getStatus());
    }

    @Test
    @DisplayName("Must throw exception when paymentId is null")
    void shouldThrowExceptionWhenPaymentIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> {
            checkPaymentStatusUseCase.execute(null);
        });
    }
} 