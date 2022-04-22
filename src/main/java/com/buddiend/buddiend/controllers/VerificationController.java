package com.buddiend.buddiend.controllers;


import com.buddiend.buddiend.services.UserService;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpResponse;

@Controller
@RequestMapping("/verify")
public class VerificationController {

    private final UserService userService;

    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getVerificationPage(HttpServletRequest request) throws IllegalAccessException {

        if(request.getSession().getAttribute("user_to_register") == null){
            throw new IllegalAccessException();
        }
        return "verify";
    }

    @PostMapping
    public String verify(@RequestParam String email, @RequestParam String verification_code){

        this.userService.verify(email, verification_code);
        return "redirect:/login";
    }

    @PutMapping("/resend")
    public Boolean resendVerifyCode(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        this.userService.resendVerificationCode(email);
        return true;
    }
}
