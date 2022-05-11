package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.models.dto.ResetPasswordDto;
import com.buddiend.buddiend.models.dto.UserRegisterDto;
import com.buddiend.buddiend.services.AuthService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/password/reset")
public class ResetPasswordController {
    private final AuthService authService;

    public ResetPasswordController(AuthService authService) {
        this.authService = authService;
    }

    @ModelAttribute("resetPass")
    public ResetPasswordDto resetPasswordDto() {
        return new ResetPasswordDto();
    }

    @GetMapping("/{token}")
    public String showResetPasswordPage(@PathVariable String token, Model model) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            return "redirect:/explore";
        }

        boolean validated = this.authService.validateToken(token);

        if (validated) {
            model.addAttribute("token", token);
            return "reset-password";
        }

        return "redirect:/login";
    }

    @PostMapping
    public String resetPassword(@ModelAttribute("resetPass") ResetPasswordDto resetPasswordDto, @RequestParam String token) {

        resetPasswordDto.setToken(token);
        this.authService.resetPassword(resetPasswordDto);

        return "redirect:/login";
    }
}
