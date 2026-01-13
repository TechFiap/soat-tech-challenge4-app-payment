package com.postechfiap_group130.techchallenge_fastfood.api.rest.handler;

import com.postechfiap_group130.techchallenge_fastfood.api.rest.dto.response.ErrorResponseDto;
import com.postechfiap_group130.techchallenge_fastfood.application.exceptions.ErrorException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void shouldHandleValidationExceptions() {
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("object", "fieldName", "must not be null");

        when(bindingResult.getFieldErrors()).thenReturn(java.util.List.of(fieldError));

        MethodArgumentNotValidException ex =
                new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<ErrorResponseDto> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("fieldName, must not be null", response.getBody().error());
    }

    @Test
    void shouldHandleErrorExceptions() {
        ErrorException ex = new ErrorException("Custom error occurred");

        ResponseEntity<ErrorResponseDto> response = handler.handleErrorExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Custom error occurred", response.getBody().error());
    }

    @Test
    void shouldHandleIllegalArgumentExceptions() {
        IllegalArgumentException ex = new IllegalArgumentException("Invalid argument");

        ResponseEntity<ErrorResponseDto> response = handler.handleIllegalArgumentExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(400, response.getBody().status());
        assertEquals("Invalid argument", response.getBody().error());
    }
}