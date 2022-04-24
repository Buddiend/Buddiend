package com.buddiend.buddiend.controllers.video;

import com.buddiend.buddiend.models.ChatRoom;
import com.buddiend.buddiend.services.ChatRoomService;
import com.buddiend.buddiend.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/video")
public class VideoChatController {
    private final UserService userService;
    private final ChatRoomService chatRoomService;

    public VideoChatController(UserService userService, ChatRoomService chatRoomService) {
        this.userService = userService;
        this.chatRoomService = chatRoomService;
    }

    @GetMapping("/{meetingId}")
    public String getVideoChatPage(@PathVariable String meetingId,
                                   Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Object principal = authentication.getPrincipal();
        String email = ((UserDetails)principal).getUsername();
        String name = this.userService.findByEmail(email).getName();
        ChatRoom chatRoom = this.chatRoomService.findByMeetingId(meetingId).get();

        model.addAttribute("meetingId", meetingId);
        model.addAttribute("name", name);
        model.addAttribute("title", chatRoom.getName());

        return "video";
    }
}
