package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/profile")
public class MyProfileController {

    private final UserService userService;

    public MyProfileController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getMyProfilePage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        User userToShow = this.userService.findByEmail(email);
        model.addAttribute("user", userToShow);
        model.addAttribute("isEditingMode", false);

        return "my-profile";
    }

    @GetMapping("/edit")
    public String getEditMyProfilePage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        User userToShow = this.userService.findByEmail(email);
        model.addAttribute("user", userToShow);
        model.addAttribute("isEditingMode", true);

        return "my-profile";
    }


    @PostMapping("/edit")
    public String submitChanges(@RequestParam String email,
                                @RequestParam String username,
                                @RequestParam(required = false) String currentPassword,
                                @RequestParam(required = false) String newPassword){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String userEmail = ((UserDetails)principal).getUsername();

        this.userService.edit(userEmail, email, username, currentPassword, newPassword);

        return "redirect:/profile";
    }
}
