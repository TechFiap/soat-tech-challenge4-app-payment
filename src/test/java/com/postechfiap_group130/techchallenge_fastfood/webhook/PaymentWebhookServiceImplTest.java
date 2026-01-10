package com.postechfiap_group130.techchallenge_fastfood.webhook;

import com.postechfiap_group130.techchallenge_fastfood.webhook.dto.PaymentWebhookRequest;
import com.postechfiap_group130.techchallenge_fastfood.webhook.exception.PaymentProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentWebhookServiceImplTest {

    @Mock
    private WebClient webClient;

    @Mock
    private WebClient.RequestBodyUriSpec requestBodyUriSpec;

    @Mock
    private WebClient.RequestBodySpec requestBodySpec;
    
    @Mock
    private WebClient.RequestHeadersSpec requestHeadersSpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;

    private PaymentWebhookServiceImpl service;

    @BeforeEach
    void setUp() {
        service = new PaymentWebhookServiceImpl(webClient);
    }

    @Test
    void shouldProcessPaymentUpdateSuccessfully() {
        // Mocking the fluent WebClient API
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenReturn(responseSpec);
        when(responseSpec.onStatus(any(), any())).thenReturn(responseSpec);
        when(responseSpec.bodyToMono(Void.class)).thenReturn(Mono.empty());

        PaymentWebhookRequest request = new PaymentWebhookRequest("123", "approved");

        service.processPaymentUpdate(request);

        verify(webClient).post();
    }

    @Test
    void shouldThrowExceptionWhenWebClientFails() {
        when(webClient.post()).thenReturn(requestBodyUriSpec);
        when(requestBodyUriSpec.uri(anyString())).thenReturn(requestBodySpec);
        when(requestBodySpec.contentType(any())).thenReturn(requestBodySpec);
        when(requestBodySpec.bodyValue(any())).thenReturn(requestHeadersSpec);
        when(requestHeadersSpec.retrieve()).thenThrow(new RuntimeException("API Error"));

        PaymentWebhookRequest request = new PaymentWebhookRequest("123", "failed");

        assertThrows(PaymentProcessingException.class, () -> service.processPaymentUpdate(request));
    }
}
