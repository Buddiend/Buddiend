package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.PasswordReset;
import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.models.dto.UserRegisterDto;
import com.buddiend.buddiend.models.enumerations.Role;
import com.buddiend.buddiend.repositories.PasswordResetRepository;
import com.buddiend.buddiend.repositories.UserRepository;
import com.buddiend.buddiend.services.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final PasswordResetRepository passwordResetRepository;


    public UserServiceImpl(UserRepository userRepository, @Lazy BCryptPasswordEncoder passwordEncoder, PasswordResetRepository passwordResetRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetRepository = passwordResetRepository;
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
    public Optional<User> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public User save(UserRegisterDto userRegisterDto) {
        User user = new User(userRegisterDto.getEmail(),
                userRegisterDto.getUsername(),
                userRegisterDto.getName(),
                passwordEncoder.encode(userRegisterDto.getPassword()),
                Role.ROLE_USER);
        return this.userRepository.save(user);
    }

    @Override
    public void createPasswordResetToken(User user, String token) {
        PasswordReset newToken = new PasswordReset(user, token);
        this.passwordResetRepository.save(newToken);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = this.userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credentials."));

        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getAuthority())));
    }
}
