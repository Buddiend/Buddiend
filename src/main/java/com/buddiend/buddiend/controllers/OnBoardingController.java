package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.models.Topic;
import com.buddiend.buddiend.services.TopicService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

@Controller
@RequestMapping("/onboarding")
public class OnBoardingController {

    private final TopicService topicService;

    public OnBoardingController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping
    public String getOnBoardingPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        List<Topic> selectedTopics = this.topicService.findSelected(email);

        model.addAttribute("topics",this.topicService.findAll());
        model.addAttribute("selectedTopics", selectedTopics == null ? new ArrayList<>() : selectedTopics );

        return "onboarding";
    }

    @PostMapping("/subscribe")
    public String subscribeTopics(@RequestParam List<Topic> topics){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();

        this.topicService.assignTopicsToUser(topics, email);

        return "redirect:/explore";
    }
}
