package com.postechfiap_group130.techchallenge_fastfood.core.usecases;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.PaymentRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.Payment;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import com.postechfiap_group130.techchallenge_fastfood.core.gateways.PaymentGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreatePaymentUseCaseTest {

    @Mock
    private PaymentGateway paymentGateway;

    @Captor
    private ArgumentCaptor<Payment> paymentCaptor;

    private CreatePaymentUseCase createPaymentUseCase;

    @BeforeEach
    void setUp() {
        createPaymentUseCase = new CreatePaymentUseCase(paymentGateway);
    }

    @Test
    void execute_WithValidRequest_ShouldCreateAndReturnPayment() {
        // Arrange
        Long orderId = 1L;
        BigDecimal amount = new BigDecimal("25.50");
        PaymentRequestDto requestDto = new PaymentRequestDto(orderId, amount);

        Payment savedPayment = new Payment("ce578a5a-4f25-4d32-8ee8-cb17384600c4", orderId, amount, PaymentStatusEnum.PENDING);
        when(paymentGateway.save(any(Payment.class))).thenReturn(savedPayment);

        // Act
        Payment result = createPaymentUseCase.execute(requestDto);

        // Assert
        assertNotNull(result);
        assertEquals(orderId, result.getOrderId());
        assertEquals(amount, result.getAmount());
        assertEquals(PaymentStatusEnum.PENDING, result.getStatus());
        
        verify(paymentGateway).save(paymentCaptor.capture());
        Payment capturedPayment = paymentCaptor.getValue();
        assertNull(capturedPayment.getId()); // ID should be null before save
        assertEquals(orderId, capturedPayment.getOrderId());
        assertEquals(amount, capturedPayment.getAmount());
        assertEquals(PaymentStatusEnum.PENDING, capturedPayment.getStatus());
    }

    @Test
    void execute_WithNullOrderId_ShouldThrowIllegalArgumentException() {
        // Arrange
        PaymentRequestDto requestDto = new PaymentRequestDto(null, BigDecimal.TEN);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createPaymentUseCase.execute(requestDto)
        );
        
        assertEquals("Order ID cannot be null", exception.getMessage());
        verifyNoInteractions(paymentGateway);
    }

    @Test
    void execute_WithNullAmount_ShouldThrowIllegalArgumentException() {
        // Arrange
        PaymentRequestDto requestDto = new PaymentRequestDto(1L, null);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createPaymentUseCase.execute(requestDto)
        );
        
        assertEquals("Amount must be greater than zero", exception.getMessage());
        verifyNoInteractions(paymentGateway);
    }

    @Test
    void execute_WithZeroAmount_ShouldThrowIllegalArgumentException() {
        // Arrange
        PaymentRequestDto requestDto = new PaymentRequestDto(1L, BigDecimal.ZERO);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createPaymentUseCase.execute(requestDto)
        );
        
        assertEquals("Amount must be greater than zero", exception.getMessage());
        verifyNoInteractions(paymentGateway);
    }

    @Test
    void execute_WithNegativeAmount_ShouldThrowIllegalArgumentException() {
        // Arrange
        PaymentRequestDto requestDto = new PaymentRequestDto(1L, new BigDecimal("-10.00"));

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> createPaymentUseCase.execute(requestDto)
        );
        
        assertEquals("Amount must be greater than zero", exception.getMessage());
        verifyNoInteractions(paymentGateway);
    }
}
