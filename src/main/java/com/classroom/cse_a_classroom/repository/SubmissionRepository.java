package com.classroom.cse_a_classroom.repository;

import com.classroom.cse_a_classroom.model.Quiz;
import com.classroom.cse_a_classroom.model.Submission;
import com.classroom.cse_a_classroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    List<Submission> findByStudent(User student);
    List<Submission> findByQuiz(Quiz quiz);
    boolean existsByStudentAndQuiz(User student, Quiz quiz);
}
