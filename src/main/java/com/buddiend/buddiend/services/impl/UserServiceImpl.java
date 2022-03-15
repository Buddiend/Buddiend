package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.repositories.UserRepository;
import com.buddiend.buddiend.services.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }
}
