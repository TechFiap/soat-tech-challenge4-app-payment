package com.postechfiap_group130.techchallenge_fastfood.mock_payment.controller;

import com.postechfiap_group130.techchallenge_fastfood.mock_payment.model.MockPaymentRequest;
import com.postechfiap_group130.techchallenge_fastfood.mock_payment.model.MockPaymentResponse;
import com.postechfiap_group130.techchallenge_fastfood.mock_payment.service.MockPaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/mock/payments")
public class MockPaymentController {

    private final MockPaymentService paymentService;

    @PostMapping
    public ResponseEntity<MockPaymentResponse> processPayment(@Valid @RequestBody MockPaymentRequest request) {
        MockPaymentResponse response = paymentService.processPayment(request);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
