package com.buddiend.buddiend.repositories;

import com.buddiend.buddiend.models.ChatRoom;
import com.buddiend.buddiend.models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.Optional;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

    Optional<ChatRoom> findByName(String name);
    Optional<ChatRoom> findByMeetingId (String meetingId);
    List<ChatRoom> findByTopic(Topic topic);

    @Query(value = "select distinct cr.topic_id from chat_rooms cr", nativeQuery = true)
    List<Long> findAllTopics();

    @Query(value = "select * from chat_rooms cr where cr.topic_id = ?1 limit 6", nativeQuery = true)
    List<ChatRoom> findByIdWithLimit(Long id);
}
