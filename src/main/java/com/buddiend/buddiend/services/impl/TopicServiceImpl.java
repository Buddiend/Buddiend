package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.Topic;
import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.models.exceptions.UserNotFoundException;
import com.buddiend.buddiend.repositories.TopicRepository;
import com.buddiend.buddiend.repositories.UserRepository;
import com.buddiend.buddiend.services.TopicService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicsRepository;
    private final UserRepository userRepository;

    public TopicServiceImpl(TopicRepository topicsRepository, UserRepository userRepository) {
        this.topicsRepository = topicsRepository;
        this.userRepository = userRepository;
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

    @Override
    public List<Topic> findSelected(String email) {
        User loggedUser = this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        if(loggedUser.getTopicsList().isEmpty())
            return null;
        else
            return loggedUser.getTopicsList();
    }

    @Override
    public Optional<User> assignTopicsToUser(List<Topic> topicsToAdd, String email) {
        User loggedUser = this.userRepository.findByEmail(email).orElseThrow(UserNotFoundException::new);
        topicsToAdd.forEach(el->{
            if(!loggedUser.getTopicsList().contains(el)){
                loggedUser.getTopicsList().add(el);
            }
        });
        return Optional.of(this.userRepository.save(loggedUser));
    }
}
