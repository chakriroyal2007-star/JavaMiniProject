package com.classroom.cse_a_classroom.controller;

import com.classroom.cse_a_classroom.model.ActivityTask;
import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.service.ActivityTaskService;
import com.classroom.cse_a_classroom.service.ClassroomService;
import com.classroom.cse_a_classroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class ActivityTaskController {

    @Autowired
    private ActivityTaskService taskService;

    @Autowired
    private ClassroomService classroomService;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    @GetMapping("/personal")
    public ResponseEntity<List<ActivityTask>> getPersonalTasks(Authentication auth) {
        return ResponseEntity.ok(taskService.getPersonalTasks(getAuthenticatedUser(auth)));
    }

    @GetMapping("/classroom/{classroomId}")
    public ResponseEntity<List<ActivityTask>> getClassroomTasks(@PathVariable Long classroomId) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(taskService.getClassroomTasks(classroom));
    }

    @PostMapping("/create")
    public ResponseEntity<ActivityTask> createTask(@RequestBody Map<String, Object> payload, Authentication auth) {
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        boolean isGlobal = (boolean) payload.getOrDefault("isGlobal", false);
        Long classroomId = payload.get("classroomId") != null ? Long.valueOf(payload.get("classroomId").toString()) : null;
        
        Classroom classroom = classroomId != null ? classroomService.getClassroomById(classroomId) : null;
        
        return ResponseEntity.ok(taskService.createTask(
                title, 
                description, 
                null, // deadline can be added later
                isGlobal, 
                getAuthenticatedUser(auth), 
                classroom
        ));
    }

    @PostMapping("/{taskId}/complete")
    public ResponseEntity<?> completeTask(@PathVariable Long taskId, Authentication auth) {
        try {
            return ResponseEntity.ok(taskService.completeTask(taskId, getAuthenticatedUser(auth)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<?> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok().build();
    }
}
