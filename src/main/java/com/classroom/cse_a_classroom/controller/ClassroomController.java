package com.classroom.cse_a_classroom.controller;

import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.repository.UserRepository;
import com.classroom.cse_a_classroom.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/classrooms")
public class ClassroomController {

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam String name, Authentication auth) {
        return ResponseEntity.ok(classroomService.createClassroom(name, getAuthenticatedUser(auth)));
    }

    @PostMapping("/join")
    public ResponseEntity<?> join(@RequestParam String joinCode, Authentication auth) {
        try {
            return ResponseEntity.ok(classroomService.joinClassroom(joinCode, getAuthenticatedUser(auth)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/teacher")
    public ResponseEntity<?> getTeacherClassrooms(Authentication auth) {
        return ResponseEntity.ok(classroomService.getTeacherClassrooms(getAuthenticatedUser(auth)));
    }

    @GetMapping("/student")
    public ResponseEntity<?> getStudentClassrooms(Authentication auth) {
        return ResponseEntity.ok(classroomService.getStudentClassrooms(getAuthenticatedUser(auth)));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<?> getClassroom(@PathVariable Long id) {
        return ResponseEntity.ok(classroomService.getClassroomById(id));
    }
}
