package com.classroom.cse_a_classroom.repository;

import com.classroom.cse_a_classroom.model.Announcement;
import com.classroom.cse_a_classroom.model.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
    List<Announcement> findByClassroomOrderByCreatedAtDesc(Classroom classroom);
}
