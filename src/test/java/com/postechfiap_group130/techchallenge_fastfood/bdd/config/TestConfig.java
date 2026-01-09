package com.postechfiap_group130.techchallenge_fastfood.bdd.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import com.postechfiap_group130.techchallenge_fastfood.core.interfaces.DataSource;
import com.postechfiap_group130.techchallenge_fastfood.core.dtos.PaymentDto;
import com.postechfiap_group130.techchallenge_fastfood.core.entities.PaymentStatusEnum;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
@Profile("test")
public class TestConfig {

    @Bean
    @Primary
    public DataSource testDataRepository() {
        return new TestDataRepository();
    }

    public static class TestDataRepository implements DataSource {
        private final ConcurrentHashMap<String, PaymentDto> payments = new ConcurrentHashMap<>();
        private String nextId = "ce578a5a-4f25-4d32-8ee8-cb17384600c4";

        @Override
        public Optional<PaymentDto> findPaymentById(String id) {
            return Optional.ofNullable(payments.get(id));
        }

        @Override
        public PaymentDto savePayment(PaymentDto paymentDto) {
            if (paymentDto.id() == null) {
                PaymentDto newPayment = new PaymentDto(
                    nextId,
                    paymentDto.orderId(),
                    paymentDto.amount(),
                    PaymentStatusEnum.PENDING
                );
                payments.put(newPayment.id(), newPayment);
                return newPayment;
            } else {
                payments.put(paymentDto.id(), paymentDto);
                return paymentDto;
            }
        }

        @Override
        public PaymentDto updatePaymentStatus(PaymentDto paymentDto) {
            if (paymentDto.id() != null && payments.containsKey(paymentDto.id())) {
                payments.put(paymentDto.id(), paymentDto);
                return paymentDto;
            }
            return null;
        }
    }
}
