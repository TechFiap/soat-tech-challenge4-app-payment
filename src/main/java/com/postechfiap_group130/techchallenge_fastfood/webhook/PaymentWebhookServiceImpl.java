package com.postechfiap_group130.techchallenge_fastfood.webhook;

import com.postechfiap_group130.techchallenge_fastfood.webhook.dto.PaymentStatusUpdateRequest;
import com.postechfiap_group130.techchallenge_fastfood.webhook.dto.PaymentWebhookRequest;
import com.postechfiap_group130.techchallenge_fastfood.webhook.exception.PaymentProcessingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentWebhookServiceImpl implements PaymentWebhookService {

    @Qualifier("webClient")
    private final WebClient webClient;

    @Override
    public void processPaymentUpdate(PaymentWebhookRequest request) {
        log.info("Processing payment status update for payment {}. Status: {}", 
                request.paymentId(), request.status());

        PaymentStatusUpdateRequest statusUpdate = new PaymentStatusUpdateRequest(
                request.status().toUpperCase()
        );

        try {
            webClient.post()
                    .uri("/payments/" + request.paymentId() + "/status")
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(statusUpdate)
                    .retrieve()
                    .onStatus(
                            status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .flatMap(error -> {
                                        log.error("Error updating payment status: {}", error);
                                        return Mono.error(new PaymentProcessingException(
                                                "Failed to update payment status: " + error));
                                    })
                    )
                    .bodyToMono(Void.class)
                    .block();

            log.info("Successfully updated status for payment {} to {}", 
                    request.paymentId(), request.status());

        } catch (Exception e) {
            log.error("Error processing payment status update for payment {}: {}", 
                    request.paymentId(), e.getMessage(), e);
            throw new PaymentProcessingException(
                    "Error processing payment status update: " + e.getMessage(), e);
        }
    }
}
