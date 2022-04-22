package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.services.ChatRoomService;
import com.buddiend.buddiend.services.LanguageService;
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

@Controller
@RequestMapping("/start-conversation")
public class StartConversationController {

    private final LanguageService languageService;
    private final TopicService topicService;
    private final ChatRoomService chatRoomService;


    public StartConversationController(LanguageService languageService, TopicService topicService, ChatRoomService chatRoomService) {
        this.languageService = languageService;
        this.topicService = topicService;
        this.chatRoomService = chatRoomService;
    }

    @GetMapping
    public String getStartConversationPage(Model model){

        model.addAttribute("languages", this.languageService.findAll());
        model.addAttribute("topics", this.topicService.findAll());

        return "start-conversation";
    }

    @PostMapping("/start")
    public String startConversation(@RequestParam String title,
                                    @RequestParam String description,
                                    @RequestParam Long topicId,
                                    @RequestParam Long languageId){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();

        this.chatRoomService.createChatRoom(title,description,topicId,languageId,email);

        return "redirect:/explore"; //for now :)
    }
}
