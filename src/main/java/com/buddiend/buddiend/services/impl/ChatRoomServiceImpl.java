package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.ChatRoom;
import com.buddiend.buddiend.models.Topic;
import com.buddiend.buddiend.repositories.ChatRoomRepository;
import com.buddiend.buddiend.services.ChatRoomService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomsRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomsRepository) {
        this.chatRoomsRepository = chatRoomsRepository;
    }

    @Override
    public List<ChatRoom> findAll() {
        return this.chatRoomsRepository.findAll();
    }

    @Override
    public Optional<ChatRoom> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ChatRoom> findByName(String name) {
        return this.chatRoomsRepository.findByName(name);
    }

    @Override
    public Optional<ChatRoom> findBySlug(String slug) {
        return Optional.empty();
    }

    @Override
    public Optional<ChatRoom> findByTopic(Topic topic) {
        return Optional.empty();
    }
}
