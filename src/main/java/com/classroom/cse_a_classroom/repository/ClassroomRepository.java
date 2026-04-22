package com.classroom.cse_a_classroom.repository;

import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByJoinCode(String joinCode);
    List<Classroom> findByTeacher(User teacher);
    List<Classroom> findByMembersContaining(User student);
}
