package com.classroom.cse_a_classroom.controller;

import com.classroom.cse_a_classroom.dto.QuizDTO;
import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.repository.UserRepository;
import com.classroom.cse_a_classroom.service.ClassroomService;
import com.classroom.cse_a_classroom.service.QuizService;
import com.classroom.cse_a_classroom.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private com.classroom.cse_a_classroom.service.ClassroomService classroomService;

    private User getAuthenticatedUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    @PostMapping("/resource")
    public ResponseEntity<?> createResource(
            @RequestParam String title, 
            @RequestParam String topic,
            @RequestParam(required = false) String description,
            @RequestParam String fileType,
            @RequestParam String fileUrl,
            @RequestParam Long classroomId,
            Authentication auth) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(resourceService.createResource(title, topic, description, fileType, fileUrl, getAuthenticatedUser(auth), classroom));
    }

    @GetMapping("/resources")
    public ResponseEntity<?> getResourcesByClassroom(@RequestParam Long classroomId) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(resourceService.getClassroomResources(classroom));
    }

    @PostMapping("/quiz")
    public ResponseEntity<?> createQuiz(@RequestBody QuizDTO quizDTO, @RequestParam Long classroomId, Authentication auth) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(quizService.createQuiz(quizDTO, getAuthenticatedUser(auth), classroom));
    }

    @GetMapping("/results")
    public ResponseEntity<?> getQuizResults(@RequestParam Long quizId) {
        return ResponseEntity.ok(quizService.getQuizSubmissions(quizId));
    }

    @DeleteMapping("/resource/{id}")
    public ResponseEntity<?> deleteResource(@PathVariable Long id) {
        resourceService.deleteResource(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/quiz/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok().build();
    }
}
