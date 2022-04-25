package com.buddiend.buddiend.controllers;

import com.buddiend.buddiend.models.ChatRoom;
import com.buddiend.buddiend.models.Topic;
import com.buddiend.buddiend.services.ChatRoomService;
import com.buddiend.buddiend.services.TopicService;
import com.buddiend.buddiend.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.*;

@Controller
@RequestMapping("/explore")
public class ExploreController {

    private final TopicService topicService;
    private final ChatRoomService chatRoomService;
    private final UserService userService;

    public ExploreController(TopicService topicService, ChatRoomService chatRoomService, UserService userService) {
        this.topicService = topicService;
        this.chatRoomService = chatRoomService;
        this.userService = userService;
    }

    @GetMapping
    public String showExplorePage(HttpServletRequest request, Model model) throws MessagingException, UnsupportedEncodingException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();

        if(!this.userService.findByEmail(email).isEnabled()){
            request.getSession().setAttribute("user_to_register", email);
            this.userService.resendVerificationCode(email);
            return "redirect:/verify";
        }

        List<Topic> selectedTopics = this.topicService.findSelected(email);
        List<ChatRoom> chatRooms = this.chatRoomService.findByTopics(selectedTopics);

        model.addAttribute("topics", this.topicService.findAll());
        model.addAttribute("subscribedTopics", selectedTopics);
        model.addAttribute("chatRooms", chatRooms);

        return "explore";
    }
}
