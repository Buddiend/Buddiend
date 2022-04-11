package com.buddiend.buddiend.models.dto;

import com.buddiend.buddiend.models.enumerations.Role;
import lombok.Data;

@Data
public class UserRegisterDto {
    String email;
    String username;
    String name;
    String password;
    Role role;
}
