package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdatePaymentRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Payment;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.PaymentGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UpdatePaymentUseCaseTest {

    @Mock
    private PaymentGateway paymentGateway;

    @InjectMocks
    private UpdatePaymentUseCase updatePaymentUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldUpdatePaymentStatusSuccessfully() {
        String paymentId = "ce578a5a-4f25-4d32-8ee8-cb17384600c4";
        String status = "APPROVED";
        UpdatePaymentRequestDto requestDto = new UpdatePaymentRequestDto(status);
        
        Payment expectedPayment = new Payment(paymentId, PaymentStatusEnum.APPROVED);
        when(paymentGateway.updatePaymentStatus(any(Payment.class))).thenReturn(expectedPayment);

        Payment result = updatePaymentUseCase.execute(paymentId, requestDto);

        assertNotNull(result);
        assertEquals(paymentId, result.getId());
        assertEquals(PaymentStatusEnum.APPROVED, result.getStatus());
        verify(paymentGateway, times(1)).updatePaymentStatus(any(Payment.class));
    }
}
