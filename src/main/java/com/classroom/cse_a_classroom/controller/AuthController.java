package com.classroom.cse_a_classroom.controller;

import com.classroom.cse_a_classroom.dto.AuthRequest;
import com.classroom.cse_a_classroom.dto.AuthResponse;
import com.classroom.cse_a_classroom.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody AuthRequest request) {
        try {
            authService.register(request);
            return ResponseEntity.ok(AuthResponse.builder().message("User registered successfully").build());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
