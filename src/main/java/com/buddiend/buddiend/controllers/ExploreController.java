package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.models.ChatRoom;
import com.buddiend.buddiend.models.Topic;
import com.buddiend.buddiend.services.ChatRoomService;
import com.buddiend.buddiend.services.TopicService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/explore")
public class ExploreController {

    private final TopicService topicService;
    private final ChatRoomService chatRoomService;

    public ExploreController(TopicService topicService, ChatRoomService chatRoomService) {
        this.topicService = topicService;
        this.chatRoomService = chatRoomService;
    }

    @GetMapping
    public String showExplorePage(Model model){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        List<Topic> selectedTopics = this.topicService.findSelected(email);
        List<ChatRoom> chatRooms = this.chatRoomService.findByTopics(selectedTopics);

        model.addAttribute("topics", this.topicService.findAll());
        model.addAttribute("subscribedTopics", selectedTopics);
        model.addAttribute("chatRooms", chatRooms);

        return "explore";
    }
}
