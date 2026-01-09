package com.postechfiap_group130.techchallenge_fastfood.core.controllers;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdatePaymentRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentControllerTest {

    private PaymentController paymentController;
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        dataSource = mock(DataSource.class);
        paymentController = new PaymentController(dataSource);
    }

    @Test
    @DisplayName("Must return status APPROVED when payment is approved")
    void shouldReturnApprovedStatus() {
        String paymentId = "ce578a5a-4f25-4d32-8ee8-cb17384600c4";
        when(dataSource.findPaymentById(paymentId)).thenReturn(Optional.of(
            new PaymentDto(paymentId, 1L, new BigDecimal("100.00"), PaymentStatusEnum.APPROVED)
        ));

        PaymentDto result = paymentController.checkPaymentStatus(paymentId);

        assertNotNull(result);
        assertEquals(paymentId, result.id());
        assertEquals(PaymentStatusEnum.APPROVED, result.status());

        verify(dataSource).findPaymentById(paymentId);
    }

    @Test
    @DisplayName("Must return status REJECTED when payment is rejected")
    void shouldReturnRejectedStatus() {
        String paymentId = "ce578a5a-4f25-4d32-8ee8-cb17384600c4";
        when(dataSource.findPaymentById(paymentId)).thenReturn(Optional.of(
            new PaymentDto(paymentId, 1L, new BigDecimal("50.00"), PaymentStatusEnum.REJECTED)
        ));

        PaymentDto result = paymentController.checkPaymentStatus(paymentId);

        assertNotNull(result);
        assertEquals(paymentId, result.id());
        assertEquals(PaymentStatusEnum.REJECTED, result.status());

        verify(dataSource).findPaymentById(paymentId);
    }

    @Test
    @DisplayName("Must return null when payment does not exist")
    void shouldReturnNullWhenPaymentDoesNotExist() {
        String paymentId = "null";
        when(dataSource.findPaymentById(paymentId)).thenReturn(Optional.empty());

        PaymentDto result = paymentController.checkPaymentStatus(paymentId);
        
        assertNull(result);
        verify(dataSource).findPaymentById(paymentId);
    }

    @Test
    @DisplayName("Should update payment status successfully")
    void shouldUpdatePaymentStatusSuccessfully() {
        String paymentId = "ce578a5a-4f25-4d32-8ee8-cb17384600c4";
        Long orderId = 2L;
        String newStatus = "APPROVED";
        UpdatePaymentRequestDto requestDto = new UpdatePaymentRequestDto(newStatus);
        
        // Mock the payment DTO that will be returned after update
        PaymentDto paymentDto = new PaymentDto(
            paymentId, 
            orderId, 
            new BigDecimal("100.00"), 
            PaymentStatusEnum.APPROVED
        );

        when(dataSource.findPaymentById(paymentId)).thenReturn(Optional.of(paymentDto));
        when(dataSource.updatePaymentStatus(any(PaymentDto.class))).thenReturn(paymentDto);
        PaymentDto result = paymentController.updatePayment(paymentId, requestDto);
        
        assertNotNull(result);
        assertEquals(paymentId, result.id());
        assertEquals(PaymentStatusEnum.APPROVED, result.status());
        
        ArgumentCaptor<PaymentDto> paymentCaptor = ArgumentCaptor.forClass(PaymentDto.class);
        verify(dataSource).updatePaymentStatus(paymentCaptor.capture());
        assertEquals(PaymentStatusEnum.APPROVED, paymentCaptor.getValue().status());
    }
}