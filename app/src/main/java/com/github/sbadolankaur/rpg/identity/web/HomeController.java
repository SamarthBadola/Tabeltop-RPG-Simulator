package com.github.sbadolankaur.rpg.identity.web;

import com.github.sbadolankaur.rpg.platform.security.AppUserPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
  @GetMapping("/")
  public String home(@AuthenticationPrincipal AppUserPrincipal principal, Model model) {
    model.addAttribute("user", principal);
    return "home";
  }
}
