package com.github.sbadolankaur.rpg.identity.web;

import com.github.sbadolankaur.rpg.identity.application.DuplicateUsernameException;
import com.github.sbadolankaur.rpg.identity.application.PasswordTooLongException;
import com.github.sbadolankaur.rpg.identity.application.RegistrationClosedException;
import com.github.sbadolankaur.rpg.identity.application.RegistrationService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class RegistrationController {
  private final RegistrationService registrationService;

  public RegistrationController(RegistrationService registrationService) {
    this.registrationService = registrationService;
  }

  @GetMapping("/register")
  public String registerForm(Model model) {
    model.addAttribute("form", new RegistrationForm());
    model.addAttribute("registrationOpen", registrationService.isRegistrationOpen());
    return "register";
  }

  @PostMapping("/register")
  public String register(
      @Valid @ModelAttribute("form") RegistrationForm form,
      BindingResult result,
      Model model,
      HttpServletResponse response) {
    if (result.hasErrors()) {
      model.addAttribute("registrationOpen", registrationService.isRegistrationOpen());
      return "register";
    }

    try {
      registrationService.register(form.getUsername(), form.getDisplayName(), form.getPassword());
    } catch (RegistrationClosedException exception) {
      model.addAttribute("registrationOpen", false);
      response.setStatus(HttpStatus.CONFLICT.value());
      return "register";
    } catch (DuplicateUsernameException exception) {
      result.rejectValue("username", "username.duplicate", exception.getMessage());
      model.addAttribute("registrationOpen", registrationService.isRegistrationOpen());
      return "register";
    } catch (PasswordTooLongException exception) {
      result.jectValue("password", "password.tooLong", exception.getMessage());
      model.addAttribute("registrationOpen", registrationService.isRegistrationOpen());
      return "register";
    }

    return "redirect:/login?registered";
  }
}
