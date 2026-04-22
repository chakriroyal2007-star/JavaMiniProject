package com.classroom.cse_a_classroom.dto;

import com.classroom.cse_a_classroom.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    private String token; // If using JWT, otherwise status
    private String name;
    private String email;
    private Role role;
    private String message;
}
