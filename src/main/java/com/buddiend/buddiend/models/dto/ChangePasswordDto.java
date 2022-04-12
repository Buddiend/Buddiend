package com.buddiend.buddiend.models.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ChangePasswordDto {
    @NotNull(message = "Password is required")
    private String password;
    @NotNull(message = "Password Confirmation is required")
    private String passwordConfirmation;
}
