package com.buddiend.buddiend.services;

import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.models.dto.UserRegisterDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.util.Optional;

public interface UserService extends UserDetailsService {
    UserDetails getUser();
    Optional<User> findByUsername(String username);
    User findByEmail(String email);
    User save (UserRegisterDto userRegisterDto) throws MessagingException, UnsupportedEncodingException;
    void createPasswordResetToken (User user, String token);
    void verify(String email, String verification_code);
    void resendVerificationCode(String email) throws MessagingException, UnsupportedEncodingException;
}
