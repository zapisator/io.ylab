package io.ylab.auth.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FileAuthenticationServiceTest {
    private FileAuthenticationService service;

    @BeforeEach
    void setUp() {
        service = FileAuthenticationService.create();
    }

    @Test
    void authenticate_shouldReturnTrueForValidCredentials() {
        boolean result = service.authenticate("admin", "admin");
        assertThat(result).isTrue();
    }

    @Test
    void authenticate_shouldReturnFalseForInvalidUsername() {
        boolean result = service.authenticate("invalidUser", "admin");
        assertThat(result).isFalse();
    }

    @Test
    void authenticate_shouldReturnFalseForInvalidPassword() {
        boolean result = service.authenticate("admin", "invalidPassword");
        assertThat(result).isFalse();
    }
}