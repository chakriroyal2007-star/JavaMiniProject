package com.classroom.cse_a_classroom.dto;

import com.classroom.cse_a_classroom.model.Role;
import lombok.Data;

@Data
public class AuthRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
}
