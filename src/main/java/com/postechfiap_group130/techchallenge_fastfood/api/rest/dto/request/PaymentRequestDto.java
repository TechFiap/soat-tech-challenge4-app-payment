package com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class PaymentRequestDto {

    @NotNull
    private Long orderId;
    @NotNull @Positive
    private BigDecimal amount;
}
