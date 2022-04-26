package com.buddiend.buddiend.services;

import com.buddiend.buddiend.models.Topic;
import com.buddiend.buddiend.models.User;

import java.util.*;

public interface TopicService {

    List<Topic> findAll();
    Topic findById(Long id);
    Optional<Topic> findByName(String name);
    List<Topic> findSelected(String email);
    Optional<User> assignTopicsToUser(List<Topic> topicsToAdd, String email);

}
