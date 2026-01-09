package com.postechfiap_group130.techchallenge_fastfood.api.rest.controller;

import com.postechfiap_group130.techchallenge_fastfood.api.data.datasource.DynamoDBDataSource;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.PaymentRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request.UpdatePaymentRequestDto;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentResourceTest {

    @Mock
    private DynamoDBDataSource dataSource;

    @InjectMocks
    private PaymentResource paymentResource;

    private PaymentDto testPaymentDto;

    @BeforeEach
    void setUp() {
        testPaymentDto = new PaymentDto(
                "ce578a5a-4f25-4d32-8ee8-cb17384600c4",
                2L,
                new BigDecimal("100.00"),
                PaymentStatusEnum.PENDING
        );
    }

    @Test
    void checkPaymentStatus_ShouldReturnStatus_WhenPaymentExists() {
        when(dataSource.findPaymentById("ce578a5a-4f25-4d32-8ee8-cb17384600c4")).thenReturn(Optional.of(testPaymentDto));

        ResponseEntity<PaymentStatusEnum> response = paymentResource.checkPaymentStatus("ce578a5a-4f25-4d32-8ee8-cb17384600c4");

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(PaymentStatusEnum.PENDING, response.getBody());
    }

    @Test
    void checkPaymentStatus_ShouldReturnNotFound_WhenPaymentDoesNotExist() {
        when(dataSource.findPaymentById("ce578a5a-4f25-4d32-8ee8-cb17384600c4")).thenReturn(Optional.empty());

        ResponseEntity<PaymentStatusEnum> response = paymentResource.checkPaymentStatus("ce578a5a-4f25-4d32-8ee8-cb17384600c4");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void createPayment_ShouldReturnCreatedPayment() {
        PaymentRequestDto requestDto = new PaymentRequestDto(2L, new BigDecimal("100.00"));
        when(dataSource.savePayment(any(PaymentDto.class))).thenReturn(testPaymentDto);

        ResponseEntity<PaymentDto> response = paymentResource.createPayment(requestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ce578a5a-4f25-4d32-8ee8-cb17384600c4", response.getBody().id());
        assertEquals(PaymentStatusEnum.PENDING, response.getBody().status());
    }

    @Test
    void updatePayment_ShouldReturnOk_WhenUpdateIsSuccessful() {
        UpdatePaymentRequestDto requestDto = new UpdatePaymentRequestDto("APPROVED");
        testPaymentDto = new PaymentDto("ce578a5a-4f25-4d32-8ee8-cb17384600c4", 2L, new BigDecimal("100.00"), PaymentStatusEnum.APPROVED);

        when(dataSource.findPaymentById("ce578a5a-4f25-4d32-8ee8-cb17384600c4")).thenReturn(Optional.of(testPaymentDto));
        when(dataSource.updatePaymentStatus(any(PaymentDto.class))).thenReturn(testPaymentDto);

        ResponseEntity<PaymentDto> response = paymentResource.updatePayment("ce578a5a-4f25-4d32-8ee8-cb17384600c4", requestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ce578a5a-4f25-4d32-8ee8-cb17384600c4", response.getBody().id());
        assertEquals(PaymentStatusEnum.APPROVED, response.getBody().status());

        verify(dataSource).updatePaymentStatus(any(PaymentDto.class));
    }

    @Test
    void updatePayment_ShouldReturnNotFound_WhenPaymentDoesNotExist() {
        UpdatePaymentRequestDto requestDto = new UpdatePaymentRequestDto("APPROVED");
        when(dataSource.findPaymentById("ce578a5a-4f25-4d32-8ee8-cb17384600c4")).thenReturn(Optional.empty());

        ResponseEntity<PaymentDto> response = paymentResource.updatePayment("ce578a5a-4f25-4d32-8ee8-cb17384600c4", requestDto);

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}