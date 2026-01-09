package com.postechfiap_group130.techchallenge_fastfood.api.data.dynamodb.repository;

import com.postechfiap_group130.techchallenge_fastfood.api.data.dynamodb.entity.PaymentDynamoDBEntity;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.socialsignin.spring.data.dynamodb.repository.DynamoDBCrudRepository;

import java.util.Optional;

@EnableScan
public interface PaymentDynamoDBRepository extends DynamoDBCrudRepository<PaymentDynamoDBEntity, String> {
    Optional<PaymentDynamoDBEntity> findByOrderId(Long orderId);
}
