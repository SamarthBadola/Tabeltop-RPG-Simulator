package com.github.sbadolankaur.rpg.identity.web;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegistrationForm {
  @NotBlank
  @Size(min = 3, max = 50)
  @Pattern(
      regexp = "^[A-Za-z0-9._-]+$",
      message = "Use only letters, numbers, periods, underscores, and hyphens.")
  private String username;

  @NotBlank
  @Size(max = 80)
  private String displayName;

  @NotBlank
  @Size(min = 12, max = 72, message = "Password must be between 12 and 72 characters.")
  private String password;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
