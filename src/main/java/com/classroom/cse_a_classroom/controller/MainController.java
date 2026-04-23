package com.classroom.cse_a_classroom.controller;

import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/api/user/me")
    @ResponseBody
    public Map<String, Object> getCurrentUser(Authentication auth) {
        if (auth == null || !auth.isAuthenticated()) {
            return null;
        }
        User user = userRepository.findByEmail(auth.getName()).orElse(null);
        Map<String, Object> response = new HashMap<>();
        if (user != null) {
            response.put("name", user.getName());
            response.put("email", user.getEmail());
            response.put("role", user.getRole());
            response.put("xp", user.getXp());
            response.put("level", user.getLevel());
            response.put("title", user.getTitle() != null ? user.getTitle() : "Novice");
        }
        return response;
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName()).orElseThrow();
        if (user.getRole() == com.classroom.cse_a_classroom.model.Role.TEACHER) {
            return "redirect:/teacher.html";
        } else {
            return "redirect:/student.html";
        }
    }
}
