package com.buddiend.buddiend.services;

import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.models.dto.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
    User save (UserRegisterDto userRegisterDto);
}
