package com.buddiend.buddiend.services;

import com.buddiend.buddiend.models.Topic;

import java.util.*;

public interface TopicService {

    List<Topic> findAll();
    Optional<Topic> findById(Long id);
    Optional<Topic> findByName(String name);

}
