package com.buddiend.buddiend.services;

import com.buddiend.buddiend.models.User;
import com.buddiend.buddiend.models.dto.ChangePasswordDto;
import com.buddiend.buddiend.models.dto.ResetPasswordDto;

public interface AuthService {
    void forgotPassword(String email);
    void resetPassword(ResetPasswordDto resetPasswordDto);
    void changePassword(ChangePasswordDto changePasswordDto);
    boolean validateToken(String token);
    void delete(User user);
}
