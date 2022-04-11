package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.models.dto.UserRegisterDto;
import com.buddiend.buddiend.services.UserService;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
public class UserRegisterController {
    private final UserService userService;

    public UserRegisterController(UserService userService) {
        this.userService = userService;
    }

    @ModelAttribute("user")
    public UserRegisterDto userRegistrationDto() {
        return new UserRegisterDto();
    }

    @GetMapping
    public String showRegisterPage() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            return "register";
        }

        return "redirect:/";
    }

    @PostMapping
    public String register(@ModelAttribute("user") UserRegisterDto registerDto) {
        this.userService.save(registerDto);
        return "refirect:/";
    }
}
