package com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UpdatePaymentRequestDto {

    @NotBlank
    private String status;
}
