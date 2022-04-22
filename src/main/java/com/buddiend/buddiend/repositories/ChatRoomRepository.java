package com.buddiend.buddiend.repositories;

import com.buddiend.buddiend.models.ChatRoom;
import com.buddiend.buddiend.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByName(String name);

    List<ChatRoom> findByTopic(Topic topic);
}
