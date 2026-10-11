package com.github.sbadolankaur.rpg.platform.security;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class LoginAttemptLimiterTest {
    private final LoginAttemptLimiter limiter = new LoginAttemptLimiter();

    @Test
    void limitsRepeatedFailuresForOneUsernameAndAddress() {
        for (int attempt = 0; attempt < 10; attempt++) {
            limiter.recordFailure("Admin", "192.0.2.10");
        }

        assertThat(limiter.isBlocked("admin", "192.0.2.10")).isTrue();
        assertThat(limiter.isBlocked("admin", "192.0.2.11")).isFalse();
    }

    @Test
    void successfulAuthenticationClearsTheUsernameAddressWindow() {
        for (int attempt = 0; attempt < 10; attempt++) {
            limiter.recordFailure("admin", "192.0.2.10");
        }

        limiter.recordSuccess("ADMIN", "192.0.2.10");

        assertThat(limiter.isBlocked("admin", "192.0.2.10")).isFalse();
    }

    @Test
    void limitsRepeatedFailuresFromOneAddressAcrossUsernames() {
        for (int attempt = 0; attempt < 60; attempt++) {
            limiter.recordFailure("user" + attempt, "192.0.2.10");
        }

        assertThat(limiter.isBlocked("new-user", "192.0.2.10")).isTrue();
    }
}
