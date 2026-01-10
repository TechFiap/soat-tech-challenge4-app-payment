package com.postechfiap_group130.techchallenge_fastfood.api.data.dynamodb.entity;

import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

class PaymentDynamoDBEntityTest {

    @Test
    void shouldCreatePaymentEntityCorrectly() {
        Long orderId = 123L;
        BigDecimal amount = new BigDecimal("50.00");

        PaymentDynamoDBEntity entity = new PaymentDynamoDBEntity(orderId, amount);

        Assertions.assertEquals(orderId, entity.getOrderId());
        Assertions.assertEquals(amount, entity.getAmount());
        Assertions.assertEquals(PaymentStatusEnum.PENDING, entity.getStatus());
        Assertions.assertNull(entity.getId()); // ID should be null before persistence
    }

    @Test
    void shouldSetAndGetValuesCorrectly() {
        PaymentDynamoDBEntity entity = new PaymentDynamoDBEntity();
        String id = "uuid-123";
        Long orderId = 456L;
        BigDecimal amount = new BigDecimal("100.00");
        PaymentStatusEnum status = PaymentStatusEnum.APPROVED;

        entity.setId(id);
        entity.setOrderId(orderId);
        entity.setAmount(amount);
        entity.setStatus(status);

        Assertions.assertEquals(id, entity.getId());
        Assertions.assertEquals(orderId, entity.getOrderId());
        Assertions.assertEquals(amount, entity.getAmount());
        Assertions.assertEquals(status, entity.getStatus());
    }
    
    @Test
    void shouldTestAllArgsConstructor() {
         String id = "uuid-789";
         Long orderId = 789L;
         BigDecimal amount = new BigDecimal("10.00");
         PaymentStatusEnum status = PaymentStatusEnum.REJECTED;
         
         PaymentDynamoDBEntity entity = new PaymentDynamoDBEntity(id, orderId, amount, status);
         
         Assertions.assertEquals(id, entity.getId());
         Assertions.assertEquals(orderId, entity.getOrderId());
         Assertions.assertEquals(amount, entity.getAmount());
         Assertions.assertEquals(status, entity.getStatus());
    }
}
