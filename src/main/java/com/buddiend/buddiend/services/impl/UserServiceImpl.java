package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.PasswordReset;
import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.models.dto.UserRegisterDto;
import com.buddiend.buddiend.models.enumerations.Role;
import com.buddiend.buddiend.models.exceptions.PasswordMatchException;
import com.buddiend.buddiend.repositories.PasswordResetRepository;
import com.buddiend.buddiend.repositories.UserRepository;
import com.buddiend.buddiend.services.EmailService;
import com.buddiend.buddiend.services.UserService;
import net.bytebuddy.utility.RandomString;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordResetRepository passwordResetRepository;
    private final EmailService emailService;


    public UserServiceImpl(UserRepository userRepository, @Lazy BCryptPasswordEncoder passwordEncoder, PasswordResetRepository passwordResetRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetRepository = passwordResetRepository;
        this.emailService = emailService;
    }

    @Override
    public UserDetails getUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }

        return null;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return this.userRepository.findByUsername(username);
    }

    @Override
    public User findByEmail(String email) {
        return this.userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("Invalid Credentials"));
    }

    @Override
    public User save(UserRegisterDto userRegisterDto) throws MessagingException, UnsupportedEncodingException {
        User user = new User(userRegisterDto.getEmail(),
                userRegisterDto.getUsername(),
                userRegisterDto.getName(),
                passwordEncoder.encode(userRegisterDto.getPassword()),
                Role.ROLE_USER);

        this.userRepository.save(user);

        this.emailService.sendVerificationEmail(user,"Verification code");

        return user;
    }

    @Override
    public User edit(String email, String newEmail, String username, String currentPassword, String newPassword) {
        User user = this.findByEmail(email);
        user.setEmail(newEmail);
        user.setUsername(username);
        if (currentPassword != null && !currentPassword.equals("") && newPassword != null && !newPassword.equals("")){
            if (!this.passwordEncoder.matches(currentPassword, user.getPassword())){
                throw new PasswordMatchException("Current Password does not match");
            }
            user.setPassword(this.passwordEncoder.encode(newPassword));
        }

        return this.userRepository.save(user);
    }

    @Override
    public void createPasswordResetToken(User user, String token) {
        PasswordReset newToken = new PasswordReset(user, token);
        this.passwordResetRepository.save(newToken);
    }

    @Override
    public void verify(String email, String verification_code) {
        User user = this.findByEmail(email);
        if(isVerificationCodeValid(user)){
            if(user.getVerification_code().equals(verification_code)){
                user.setEnabled(true);
                invalidateVerificationCode(user);
            }
            else
                throw new UsernameNotFoundException("Wrong verification code");
        }
        else {
            invalidateVerificationCode(user);
            throw new UsernameNotFoundException("Verification code expired");
        }
    }

    private boolean isVerificationCodeValid (User user) {
        long diff = ChronoUnit.MINUTES.between(user.getVerification_code_created_at(), LocalDateTime.now());
        return diff < 10;
    }

    private void invalidateVerificationCode(User user){
        user.setVerification_code(null);
        user.setVerification_code_created_at(null);
        this.userRepository.save(user);

    }

    @Override
    public void resendVerificationCode(String email) throws MessagingException, UnsupportedEncodingException {
        User user = this.findByEmail(email);
        user.setVerification_code(RandomString.make(6).toUpperCase());
        this.userRepository.save(user);
        this.emailService.sendVerificationEmail(user,"Verification code");
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.findByEmail(username);

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getAuthority())));
    }
}
