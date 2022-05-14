package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.ChatRoom;
import com.buddiend.buddiend.models.Language;
import com.buddiend.buddiend.models.Topic;
import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.models.exceptions.LanguageNotFoundException;
import com.buddiend.buddiend.models.exceptions.TopicNotFoundException;
import com.buddiend.buddiend.models.exceptions.UserNotFoundException;
import com.buddiend.buddiend.models.exceptions.VideoChatNotFoundException;
import com.buddiend.buddiend.repositories.ChatRoomRepository;
import com.buddiend.buddiend.repositories.LanguageRepository;
import com.buddiend.buddiend.repositories.TopicRepository;
import com.buddiend.buddiend.repositories.UserRepository;
import com.buddiend.buddiend.services.ChatRoomService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomsRepository;
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final LanguageRepository languageRepository;

    public ChatRoomServiceImpl(ChatRoomRepository chatRoomsRepository, UserRepository userRepository, TopicRepository topicRepository, LanguageRepository languageRepository) {
        this.chatRoomsRepository = chatRoomsRepository;
        this.userRepository = userRepository;
        this.topicRepository = topicRepository;
        this.languageRepository = languageRepository;
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
    public Optional<ChatRoom> findByMeetingId(String meetingId) {
        return Optional.ofNullable(this.chatRoomsRepository.findByMeetingId(meetingId)
                .orElseThrow(VideoChatNotFoundException::new));
    }


    @Override
    public List<ChatRoom> findByTopics(List<Topic> topics) {

        List<ChatRoom> chatRooms = new ArrayList<>();

        topics.forEach(el -> {
            List<ChatRoom> temp = this.chatRoomsRepository.findByTopic(el);
            if(temp != null) {
                chatRooms.addAll(temp);
            }
        });

        return chatRooms;
    }


    @Override
    public Optional<ChatRoom> createChatRoom(String title, String description, Long topicId, Long languageId, String email, String meetingId) {
        User creationUser = this.userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException :: new);
        Topic topic = this.topicRepository.findById(topicId)
                .orElseThrow(() -> new TopicNotFoundException(topicId));
        Language language = this.languageRepository.findById(languageId)
                .orElseThrow(() -> new LanguageNotFoundException(languageId));

        ChatRoom chatRoom = new ChatRoom(title, description, topic, creationUser, language, meetingId);

        return Optional.of(this.chatRoomsRepository.save(chatRoom));
    }

    @Override
    public List<Topic> findAllTopics() {
        List<Topic> topics = new ArrayList<>();
        List<Long> topic_idList = this.chatRoomsRepository.findAllTopics();

        topic_idList.forEach(el -> topics.add(this.topicRepository.findById(el).orElseThrow(() -> new TopicNotFoundException(el))));

        return topics;
    }

    @Override
    public List<ChatRoom> findAllWithLimit() {
        List<ChatRoom> chatRoomsToSend = new ArrayList<>();
        List<Long> topicIds = this.chatRoomsRepository.findAllTopics();
        topicIds.forEach(el-> chatRoomsToSend.addAll(this.chatRoomsRepository.findByIdWithLimit(el)));

        return chatRoomsToSend;
    }
}
