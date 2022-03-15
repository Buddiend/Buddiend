package com.buddiend.buddiend.services;

import com.buddiend.buddiend.models.User;

public interface AuthService {

    void login(String email, String password);
    void register(String email, String username, String password, String password_confirm, String name, String location);
    void logout(User user);
    void forgotPassword(String email);
    void resetPassword(String email, String password_new, String password_confirm, String token);
    void changePassword(User user, String password_new, String password_confirm);
    void validateToken(String token);
    void delete(User user);
}
