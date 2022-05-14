package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.PasswordReset;
import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.models.dto.ResetPasswordDto;
import com.buddiend.buddiend.models.exceptions.InvalidTokenException;
import com.buddiend.buddiend.models.exceptions.PasswordMatchException;
import com.buddiend.buddiend.repositories.PasswordResetRepository;
import com.buddiend.buddiend.repositories.UserRepository;
import com.buddiend.buddiend.services.AuthService;
import com.buddiend.buddiend.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordResetRepository passwordResetRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthServiceImpl(UserService userService, UserRepository userRepository, PasswordResetRepository passwordResetRepository, @Lazy BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.passwordResetRepository = passwordResetRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void forgotPassword(String email) {
        User user = getUserFromEmail(email);
        String token = UUID.randomUUID().toString();
        this.userService.createPasswordResetToken(user, token);
    }

    @Override
    public void resetPassword(ResetPasswordDto resetPasswordDto) {
        User user = this.getUserFromToken(this.getPasswordResetFromToken(resetPasswordDto.getToken()));

        if (!resetPasswordDto.getPassword().equals(resetPasswordDto.getPasswordConfirmation())) {
            throw new PasswordMatchException("Password's do not match!");
        }

        user.setPassword(passwordEncoder.encode(resetPasswordDto.getPassword()));
        this.userRepository.save(user);
    }

    @Override
    public boolean validateToken(String token) {
        final PasswordReset passwordReset = this.getPasswordResetFromToken(token);
        return isTokenValid(passwordReset);
    }

    @Override
    public void delete(User user) {

    }

    private User getUserFromEmail (String email) {
        return this.userService.findByEmail(email);
    }

    private boolean isTokenValid(PasswordReset passwordReset) {
        long diff = ChronoUnit.HOURS.between(passwordReset.getCreated_at().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime(), LocalDateTime.now());
        return diff <= 1;
    }

    public User getUserFromToken(PasswordReset passwordReset) {
        return this.userService.findByEmail(passwordReset.getUser().getEmail());
    }

    private PasswordReset getPasswordResetFromToken (String token) {
        return this.passwordResetRepository.findByToken(token).orElseThrow(() -> new InvalidTokenException("Invalid token"));
    }
}
