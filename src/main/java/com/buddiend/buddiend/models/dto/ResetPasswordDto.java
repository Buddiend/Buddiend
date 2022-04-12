package com.buddiend.buddiend.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ResetPasswordDto {
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Password Confirmation is required")
    private String passwordConfirmation;
    @NotNull(message = "Token is required")
    private String token;
}
