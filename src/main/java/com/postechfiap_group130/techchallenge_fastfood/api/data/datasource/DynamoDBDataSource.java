package com.postechfiap_group130.techchallenge_fastfood.api.data.datasource;

import com.postechfiap_group130.techchallenge_fastfood.api.data.dynamodb.entity.PaymentDynamoDBEntity;
import com.postechfiap_group130.techchallenge_fastfood.api.data.dynamodb.repository.PaymentDynamoDBRepository;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Profile("!test")
@Component
public class DynamoDBDataSource implements DataSource {

    private final PaymentDynamoDBRepository paymentDynamoDBRepository;

    public DynamoDBDataSource(PaymentDynamoDBRepository paymentDynamoDBRepository) {
        this.paymentDynamoDBRepository = paymentDynamoDBRepository;
    }

    @Override
    public PaymentDto savePayment(PaymentDto paymentDto) {
        PaymentDynamoDBEntity entity = toEntity(paymentDto);
        PaymentDynamoDBEntity savedEntity = paymentDynamoDBRepository.save(entity);
        return toDto(savedEntity);
    }

    @Override
    public Optional<PaymentDto> findPaymentById(String paymentId) {
        return paymentDynamoDBRepository.findById(paymentId)
                .map(this::toDto);
    }

    @Override
    public PaymentDto updatePaymentStatus(PaymentDto paymentDto) {
        return paymentDynamoDBRepository.findById(String.valueOf(paymentDto.id()))
                .map(entity -> {
                    entity.setStatus(paymentDto.status());
                    PaymentDynamoDBEntity updatedEntity = paymentDynamoDBRepository.save(entity);
                    return toDto(updatedEntity);
                })
                .orElseThrow(() -> new RuntimeException("Payment not found with id: " + paymentDto.id()));
    }

    private PaymentDynamoDBEntity toEntity(PaymentDto dto) {
        PaymentDynamoDBEntity entity = new PaymentDynamoDBEntity();
        if (dto.id() != null) {
            entity.setId(dto.id());
        }
        entity.setOrderId(dto.orderId());
        entity.setAmount(dto.amount());
        entity.setStatus(dto.status());
        return entity;
    }

    private PaymentDto toDto(PaymentDynamoDBEntity entity) {
        return new PaymentDto(
                entity.getId(),
                entity.getOrderId(),
                entity.getAmount(),
                entity.getStatus()
        );
    }
}
