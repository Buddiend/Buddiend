package com.buddiend.buddiend.services;


import com.buddiend.buddiend.models.User;

import java.util.Optional;

public interface UserService {

    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);

}
