package com.postechfiap_group130.techchallenge_fastfood.webhook.controller;

import com.postechfiap_group130.techchallenge_fastfood.webhook.PaymentWebhookService;
import com.postechfiap_group130.techchallenge_fastfood.webhook.dto.PaymentWebhookRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/webhook/payments")
@RequiredArgsConstructor
public class PaymentWebhookController {

    private final PaymentWebhookService paymentWebhookService;

    @PostMapping
    public ResponseEntity<Void> handlePaymentUpdate(@Valid @RequestBody PaymentWebhookRequest request) {
        paymentWebhookService.processPaymentUpdate(request);
        return ResponseEntity.ok().build();
    }
}
