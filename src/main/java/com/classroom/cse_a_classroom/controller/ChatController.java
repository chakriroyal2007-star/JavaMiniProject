package com.classroom.cse_a_classroom.controller;

import com.classroom.cse_a_classroom.model.*;
import com.classroom.cse_a_classroom.repository.UserRepository;
import com.classroom.cse_a_classroom.service.ChatService;
import com.classroom.cse_a_classroom.service.ClassroomService;
import com.classroom.cse_a_classroom.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.util.List;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private FileStorageService fileStorageService;

    private User getAuthenticatedUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    @GetMapping("/group/{classroomId}")
    public ResponseEntity<List<ChatMessage>> getGroupMessages(@PathVariable Long classroomId) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(chatService.getGroupMessages(classroom));
    }

    @PostMapping("/group/{classroomId}")
    public ResponseEntity<ChatMessage> sendGroupMessage(
            @PathVariable Long classroomId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) MultipartFile file,
            Authentication auth) throws Exception {
        
        Classroom classroom = classroomService.getClassroomById(classroomId);
        String fileUrl = null;
        String fileName = null;
        String fileType = null;

        if (file != null && !file.isEmpty()) {
            File tempFile = File.createTempFile("chat_", file.getOriginalFilename());
            file.transferTo(tempFile);
            fileName = file.getOriginalFilename();
            fileUrl = "/uploads/" + fileStorageService.storeFile(tempFile);
            fileType = file.getContentType();
        }

        return ResponseEntity.ok(chatService.sendGroupMessage(
            getAuthenticatedUser(auth), classroom, content, fileUrl, fileName, fileType
        ));
    }

    @GetMapping("/personal/{receiverId}")
    public ResponseEntity<List<ChatMessage>> getPersonalMessages(@PathVariable Long receiverId, Authentication auth) {
        User sender = getAuthenticatedUser(auth);
        User receiver = userRepository.findById(receiverId).orElseThrow();
        return ResponseEntity.ok(chatService.getPersonalMessages(sender, receiver));
    }

    @PostMapping("/personal/{receiverId}")
    public ResponseEntity<ChatMessage> sendPersonalMessage(
            @PathVariable Long receiverId,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) MultipartFile file,
            Authentication auth) throws Exception {
        
        User sender = getAuthenticatedUser(auth);
        User receiver = userRepository.findById(receiverId).orElseThrow();
        
        String fileUrl = null;
        String fileName = null;
        String fileType = null;

        if (file != null && !file.isEmpty()) {
            File tempFile = File.createTempFile("chat_", file.getOriginalFilename());
            file.transferTo(tempFile);
            fileName = file.getOriginalFilename();
            fileUrl = "/uploads/" + fileStorageService.storeFile(tempFile);
            fileType = file.getContentType();
        }

        return ResponseEntity.ok(chatService.sendPersonalMessage(
            sender, receiver, content, fileUrl, fileName, fileType
        ));
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(chatService.getAllUsers());
    }
}
