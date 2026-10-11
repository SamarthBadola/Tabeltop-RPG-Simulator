package com.github.sbadolankaur.rpg.identity.application;

public class DuplicateUsernameException extends RuntimeException {
    public DuplicateUsernameException() {
        super("That username is already in use.");
    }
}
