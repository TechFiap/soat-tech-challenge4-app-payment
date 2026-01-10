package com.postechfiap_group130.techchallenge_fastfood.webhook.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postechfiap_group130.techchallenge_fastfood.webhook.PaymentWebhookService;
import com.postechfiap_group130.techchallenge_fastfood.webhook.dto.PaymentWebhookRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PaymentWebhookController.class,
    excludeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        // Exclude configs that might trigger full context loading or security beans we don't need
    }))
class PaymentWebhookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaymentWebhookService paymentWebhookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void shouldHandlePaymentUpdateSuccessfully() throws Exception {
        PaymentWebhookRequest request = new PaymentWebhookRequest("12345", "APPROVED");

        mockMvc.perform(post("/webhook/payments")
                        .with(csrf()) // Needed if CSRF is enabled by default in Security Config
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());

        verify(paymentWebhookService).processPaymentUpdate(any(PaymentWebhookRequest.class));
    }
}
