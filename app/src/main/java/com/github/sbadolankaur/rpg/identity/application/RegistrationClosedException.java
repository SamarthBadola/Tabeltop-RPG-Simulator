package com.github.sbadolankaur.rpg.identity.application;

public class RegistrationClosedException extends RuntimeException {
    public RegistrationClosedException() {
        super("First-account registration has already been completed.");
    }
}
