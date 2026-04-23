package com.classroom.cse_a_classroom.controller;

import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.repository.SubmissionRepository;
import com.classroom.cse_a_classroom.repository.UserRepository;
import com.classroom.cse_a_classroom.service.ClassroomService;
import com.classroom.cse_a_classroom.service.QuizService;
import com.classroom.cse_a_classroom.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private QuizService quizService;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private com.classroom.cse_a_classroom.service.ClassroomService classroomService;

    @Autowired
    private UserRepository userRepository;

    private User getAuthenticatedUser(Authentication auth) {
        return userRepository.findByEmail(auth.getName()).orElseThrow();
    }

    @GetMapping("/resources")
    public ResponseEntity<?> getResources(@RequestParam Long classroomId) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(resourceService.getClassroomResources(classroom));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<?> getQuizzes(@RequestParam Long classroomId) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(quizService.getPublishedQuizzes(classroom));
    }

    @GetMapping("/quiz/{id}")
    public ResponseEntity<?> getQuiz(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getQuizById(id));
    }

    @PostMapping("/quiz/validate")
    public ResponseEntity<?> validateQuizPassword(@RequestParam Long quizId, @RequestParam String password) {
        boolean isValid = quizService.validatePassword(quizId, password);
        if (isValid) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(401).body("Invalid password");
        }
    }

    @PostMapping("/quiz/submit")
    public ResponseEntity<?> submitQuiz(@RequestParam Long quizId, 
                                     @RequestParam(required = false) Integer timeTaken,
                                     @RequestBody Map<Long, String> answers, 
                                     Authentication auth) {
        try {
            return ResponseEntity.ok(quizService.submitQuiz(quizId, getAuthenticatedUser(auth), answers, timeTaken));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<?> getLeaderboard() {
        return ResponseEntity.ok(submissionRepository.findAll()); // Simple for now, can be aggregated
    }
}
