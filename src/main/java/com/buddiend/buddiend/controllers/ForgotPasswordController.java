package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.services.AuthService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.validation.constraints.Email;

@Controller
@RequestMapping("/forgot-password")
@Validated
public class ForgotPasswordController {
    private final AuthService authService;

    public ForgotPasswordController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String showForgotPasswordPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "forgot-password";
        }

        return "redirect:/";
    }

    @PostMapping
    public void forgotPassword(@Email @RequestParam("email") String email) {
        this.authService.forgotPassword(email);
    }
}
