package com.buddiend.buddiend.controllers;


import com.buddiend.buddiend.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/verify")
public class VerificationController {

    private final UserService userService;

    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getVerificationPage(Model model){

        return "verify";
    }

    @PostMapping
    public String verify(@RequestParam String email, @RequestParam String verification_code){

        this.userService.verify(email, verification_code);
        return "redirect:/";
    }
}
