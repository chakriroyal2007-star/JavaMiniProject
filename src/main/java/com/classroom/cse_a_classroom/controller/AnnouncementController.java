package com.classroom.cse_a_classroom.controller;

import com.classroom.cse_a_classroom.model.Announcement;
import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.repository.AnnouncementRepository;
import com.classroom.cse_a_classroom.service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private ClassroomService classroomService;

    @PostMapping
    public ResponseEntity<?> create(@RequestParam Long classroomId, @RequestParam String title, @RequestParam String content) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        Announcement announcement = Announcement.builder()
                .title(title)
                .content(content)
                .classroom(classroom)
                .build();
        return ResponseEntity.ok(announcementRepository.save(announcement));
    }

    @GetMapping
    public ResponseEntity<List<Announcement>> getByClassroom(@RequestParam Long classroomId) {
        Classroom classroom = classroomService.getClassroomById(classroomId);
        return ResponseEntity.ok(announcementRepository.findByClassroomOrderByCreatedAtDesc(classroom));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        announcementRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
