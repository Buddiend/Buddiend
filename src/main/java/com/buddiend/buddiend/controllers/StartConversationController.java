package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.services.ChatRoomService;
import com.buddiend.buddiend.services.LanguageService;
import com.buddiend.buddiend.services.TopicService;
import com.buddiend.buddiend.services.UserService;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/start-conversation")
public class StartConversationController {

    private final LanguageService languageService;
    private final TopicService topicService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;


    public StartConversationController(LanguageService languageService, TopicService topicService, ChatRoomService chatRoomService, UserService userService) {
        this.languageService = languageService;
        this.topicService = topicService;
        this.chatRoomService = chatRoomService;
        this.userService = userService;
    }

    @GetMapping
    public String getStartConversationPage(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();

        model.addAttribute("languages", this.languageService.findAll());
        model.addAttribute("topics", this.topicService.findAll());
        model.addAttribute("user", this.userService.findByEmail(email));

        return "start-conversation";
    }

    @PostMapping
    public void startConversation(@RequestBody String requestBody) throws ParseException {
        JSONParser parser = new JSONParser();
        String title;
        String description;
        long topicId;
        long languageId;
        String meetingId;

        JSONObject jsonBody = (JSONObject) parser.parse(requestBody);
        title = (String) jsonBody.get("title");
        description = (String) jsonBody.get("description");
        topicId = Long.parseLong((String) jsonBody.get("topicId"));
        languageId = Long.parseLong((String) jsonBody.get("languageId"));
        meetingId = (String) jsonBody.get("meetingId");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();

        this.chatRoomService.createChatRoom(title,description,topicId,languageId,email,meetingId);
    }
}
