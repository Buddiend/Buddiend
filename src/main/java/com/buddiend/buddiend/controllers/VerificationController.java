package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;

@Controller
@RequestMapping("/verify")
public class VerificationController {

    private final UserService userService;

    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getVerificationPage(HttpServletRequest request) {

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        Object principal = authentication.getPrincipal();
//        String email = ((UserDetails)principal).getUsername();

        if(request.getSession().getAttribute("user_to_register") == null){
            return "redirect:/explore";
            //throw new IllegalAccessException();
        }

        return "verify";
    }

    @PostMapping
    public String verify(@RequestParam String email, @RequestParam String verification_code, HttpServletRequest request){

        this.userService.verify(email, verification_code);
        request.getSession().invalidate();

        return "redirect:/login";
    }

    @PutMapping("/resend")
    public Boolean resendVerifyCode(@RequestParam String email) throws MessagingException, UnsupportedEncodingException {
        this.userService.resendVerificationCode(email);
        return true;
    }
}
