package com.postechfiap_group130.techchallenge_fastfood.api.data.datasource;

import com.postechfiap_group130.techchallenge_fastfood.api.data.dynamodb.entity.PaymentDynamoDBEntity;
import com.postechfiap_group130.techchallenge_fastfood.api.data.dynamodb.repository.PaymentDynamoDBRepository;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DynamoDBDataSourceTest {

    @Mock
    private PaymentDynamoDBRepository paymentRepository;

    @InjectMocks
    private DynamoDBDataSource dataSource;

    private PaymentDto testPaymentDto;
    private PaymentDynamoDBEntity testEntity;

    @BeforeEach
    void setUp() {
        testPaymentDto = new PaymentDto(
                "ce578a5a-4f25-4d32-8ee8-cb17384600c4",
                2L,
                new BigDecimal("100.00"),
                PaymentStatusEnum.PENDING
        );

        testEntity = new PaymentDynamoDBEntity();
        testEntity.setId("ce578a5a-4f25-4d32-8ee8-cb17384600c4");
        testEntity.setOrderId(2L);
        testEntity.setAmount(new BigDecimal("100.00"));
        testEntity.setStatus(PaymentStatusEnum.PENDING);
    }

    @Test
    void savePayment_ShouldReturnSavedPayment() {
        when(paymentRepository.save(any(PaymentDynamoDBEntity.class))).thenReturn(testEntity);

        PaymentDto result = dataSource.savePayment(testPaymentDto);

        assertNotNull(result);
        assertEquals("ce578a5a-4f25-4d32-8ee8-cb17384600c4", result.id());
        assertEquals(2L, result.orderId());
        assertEquals(new BigDecimal("100.00"), result.amount());
        assertEquals(PaymentStatusEnum.PENDING, result.status());
    }

    @Test
    void findPaymentById_ShouldReturnPayment_WhenExists() {
        when(paymentRepository.findById("ce578a5a-4f25-4d32-8ee8-cb17384600c4")).thenReturn(Optional.of(testEntity));

        Optional<PaymentDto> result = dataSource.findPaymentById("ce578a5a-4f25-4d32-8ee8-cb17384600c4");

        assertTrue(result.isPresent());
        assertEquals("ce578a5a-4f25-4d32-8ee8-cb17384600c4", result.get().id());
        assertEquals(2L, result.get().orderId());
    }

    @Test
    void findPaymentById_ShouldReturnEmpty_WhenNotExists() {
        when(paymentRepository.findById("ce578a5a-4f25-4d32-8ee8-cb17384600c4")).thenReturn(Optional.empty());

        Optional<PaymentDto> result = dataSource.findPaymentById("ce578a5a-4f25-4d32-8ee8-cb17384600c4");

        assertTrue(result.isEmpty());
    }

    @Test
    void updatePaymentStatus_ShouldUpdateStatus_WhenPaymentExists() {
        PaymentDto updatedDto = new PaymentDto("ce578a5a-4f25-4d32-8ee8-cb17384600c4", 2L, new BigDecimal("100.00"), PaymentStatusEnum.APPROVED);
        PaymentDynamoDBEntity updatedEntity = new PaymentDynamoDBEntity();
        updatedEntity.setId("ce578a5a-4f25-4d32-8ee8-cb17384600c4");
        updatedEntity.setOrderId(2L);
        updatedEntity.setAmount(new BigDecimal("100.00"));
        updatedEntity.setStatus(PaymentStatusEnum.APPROVED);

        when(paymentRepository.findById("ce578a5a-4f25-4d32-8ee8-cb17384600c4")).thenReturn(Optional.of(testEntity));
        when(paymentRepository.save(any(PaymentDynamoDBEntity.class))).thenReturn(updatedEntity);

        PaymentDto result = dataSource.updatePaymentStatus(updatedDto);

        assertNotNull(result);
        assertEquals(PaymentStatusEnum.APPROVED, result.status());
        verify(paymentRepository).save(any(PaymentDynamoDBEntity.class));
    }

    @Test
    void updatePaymentStatus_ShouldThrow_WhenPaymentNotFound() {
        PaymentDto updatedDto = new PaymentDto("ce578a5a-4f25-4d32-8ee8-cb17384600c4", 2L, new BigDecimal("100.00"), PaymentStatusEnum.APPROVED);
        when(paymentRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                dataSource.updatePaymentStatus(updatedDto)
        );
    }
}