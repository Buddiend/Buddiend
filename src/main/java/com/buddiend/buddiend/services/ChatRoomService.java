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
    Optional<ChatRoom> findByTopic(Topic topic);



}
