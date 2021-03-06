package com.buddiend.buddiend.repositories;

import com.buddiend.buddiend.models.PasswordReset;
import com.buddiend.buddiend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetRepository extends JpaRepository<PasswordReset, Long> {
    Optional<PasswordReset> findByToken (String token);
    Optional<PasswordReset> findByUser (User user);
}
