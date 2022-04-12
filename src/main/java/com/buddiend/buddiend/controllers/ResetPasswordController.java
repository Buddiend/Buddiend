package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.models.dto.ResetPasswordDto;
import com.buddiend.buddiend.services.AuthService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/password/reset")
public class ResetPasswordController {
    private final AuthService authService;

    public ResetPasswordController(AuthService authService) {
        this.authService = authService;
    }

    @GetMapping
    public String showResetPasswordPage(@PathVariable String token) {
        boolean validated = this.authService.validateToken(token);

        if (validated) {
            return "reset-password";
        }

        return "redirect:/login";
    }

    @PostMapping
    public String resetPassword(@Valid ResetPasswordDto resetPasswordDto) {
        boolean validated = this.authService.validateToken(resetPasswordDto.getToken());

        if (!validated) {
            return "redirect:/login";
        }

        this.authService.resetPassword(resetPasswordDto);
        return "redirect:/";
    }
}
