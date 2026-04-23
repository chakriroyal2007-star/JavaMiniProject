package com.classroom.cse_a_classroom.repository;

import com.classroom.cse_a_classroom.model.Quiz;
import com.classroom.cse_a_classroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.classroom.cse_a_classroom.model.Classroom;

import java.util.List;

public interface QuizRepository extends JpaRepository<Quiz, Long> {
    List<Quiz> findByTeacher(User teacher);
    List<Quiz> findByClassroom(Classroom classroom);
    List<Quiz> findByClassroomAndStatus(Classroom classroom, String status);
}
