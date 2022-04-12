package com.buddiend.buddiend.models.dto;

import com.buddiend.buddiend.models.enumerations.Role;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class UserRegisterDto {
    @NotNull(message = "Email is required")
    String email;
    @NotNull(message = "Username is required")
    String username;
    @NotNull(message = "Full Name is required")
    String name;
    @NotNull(message = "Password is required")
    String password;
    Role role;
}
