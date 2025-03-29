package com.consentframework.shared.api.domain.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class BadRequestExceptionTest {
    @Test
    void testWithCause() {
        final String errorMessage = "Test error message";
        final Throwable cause = new RuntimeException("Underlying cause");
        final BadRequestException exception = new BadRequestException(errorMessage, cause);

        assertEquals(errorMessage, exception.getMessage());
        assertEquals(cause, exception.getCause());
    }
}
