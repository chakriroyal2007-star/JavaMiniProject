package com.classroom.cse_a_classroom.controller;

import java.io.File;

import com.classroom.cse_a_classroom.dto.QuizDTO;
import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.Resource;
import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.repository.UserRepository;
import com.classroom.cse_a_classroom.service.ClassroomService;
import com.classroom.cse_a_classroom.service.QuizService;
import com.classroom.cse_a_classroom.service.ResourceService;
import com.classroom.cse_a_classroom.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    private ClassroomService classroomService;

    @Autowired
    private FileStorageService fileStorageService;

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

    @GetMapping("/resources/upload")
    @ResponseBody
    public String uploadInfo() {
        return "This endpoint only supports POST requests for file uploads. Please use the dashboard interface.";
    }

    @PostMapping("/resources/upload")
    public ResponseEntity<Resource> uploadResource(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("topic") String topic,
            @RequestParam("description") String description,
            @RequestParam("classId") Long classId,
            Authentication auth) throws Exception {
        
        User teacher = getAuthenticatedUser(auth);
        Classroom classroom = classroomService.getClassroomById(classId);

        // Convert MultipartFile to temporary File to use FileStorageService
        File tempFile = File.createTempFile("upload_", file.getOriginalFilename());
        file.transferTo(tempFile);
        
        String fileName = fileStorageService.storeFile(tempFile);
        
        String ext = file.getOriginalFilename().toLowerCase();
        String type = "PDF";
        if(ext.endsWith(".ppt") || ext.endsWith(".pptx")) type = "PPT";
        else if(ext.endsWith(".xls") || ext.endsWith(".xlsx")) type = "EXCEL";

        Resource resource = resourceService.createResource(
            title, topic, description, type, "/uploads/" + fileName, teacher, classroom
        );
        
        return ResponseEntity.ok(resource);
    }

    @PostMapping("/quiz")
    public ResponseEntity<?> createQuiz(@RequestBody QuizDTO quizDTO, @RequestParam Long classroomId, Authentication auth) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(quizService.createQuiz(quizDTO, getAuthenticatedUser(auth), classroom));
    }

    @PostMapping("/quiz/{id}/publish")
    public ResponseEntity<?> publishQuiz(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.publishQuiz(id));
    }

    @PostMapping("/quiz/{id}/unpublish")
    public ResponseEntity<?> unpublishQuiz(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.unpublishQuiz(id));
    }

    @GetMapping("/quiz/{id}/leaderboard")
    public ResponseEntity<?> getLeaderboard(@PathVariable Long id) {
        return ResponseEntity.ok(quizService.getLeaderboard(id));
    }

    @GetMapping("/results")
    public ResponseEntity<?> getQuizResults(@RequestParam Long quizId) {
        return ResponseEntity.ok(quizService.getQuizSubmissions(quizId));
    }

    @GetMapping("/quizzes")
    public ResponseEntity<?> getQuizzes(@RequestParam Long classroomId) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(quizService.getClassroomQuizzes(classroom));
    }

    @GetMapping("/classroom/analytics")
    public ResponseEntity<?> getClassroomAnalytics(@RequestParam Long classroomId) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(quizService.getClassroomPerformance(classroom));
    }


    @DeleteMapping("/quiz/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable Long id) {
        quizService.deleteQuiz(id);
        return ResponseEntity.ok().build();
    }
}
