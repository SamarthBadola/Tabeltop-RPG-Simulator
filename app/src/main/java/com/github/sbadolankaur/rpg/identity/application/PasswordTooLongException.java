package com.github.sbadolankaur.rpg.identity.application;

public class PasswordTooLongException extends RuntimeException {
  public PasswordTooLongException() {
    super("Password must be no more than 72 UTF-8 bytes.");
  }
}
