package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.services.AuthService;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Override
    public void forgotPassword(String email) {

    }

    @Override
    public void resetPassword(String email, String password_new, String password_confirm, String token) {

    }

    @Override
    public void changePassword(User user, String password_new, String password_confirm) {

    }

    @Override
    public void validateToken(String token) {

    }

    @Override
    public void delete(User user) {

    }
}
