package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.Topic;
import com.buddiend.buddiend.repositories.TopicRepository;
import com.buddiend.buddiend.services.TopicService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicsRepository;

    public TopicServiceImpl(TopicRepository topicsRepository) {
        this.topicsRepository = topicsRepository;
    }

    @Override
    public List<Topic> findAll() {
        return this.topicsRepository.findAll();
    }

    @Override
    public Optional<Topic> findById(Long id) {
        return this.topicsRepository.findById(id);
    }

    @Override
    public Optional<Topic> findByName(String name) {
        return this.topicsRepository.findByName(name);
    }
}
