package com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response;

public record ErrorResponseDto(
    Integer status,
    String error
) {}

