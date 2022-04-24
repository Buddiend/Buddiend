package com.buddiend.buddiend.services;

import com.buddiend.buddiend.models.ChatRoom;
import com.buddiend.buddiend.models.Topic;
import java.util.List;
import java.util.Optional;

public interface ChatRoomService {

    List<ChatRoom> findAll();
    Optional<ChatRoom> findById(Long id);
    Optional<ChatRoom> findByName(String name);
    Optional<ChatRoom> findBySlug(String slug);
    Optional<ChatRoom> findByMeetingId(String meetingId);
    List<ChatRoom> findByTopics(List<Topic> topics);
    Optional<ChatRoom> createChatRoom(String title, String description, Long topicId, Long languageId, String email, String meetingId);
}
