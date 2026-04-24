package com.classroom.cse_a_classroom.repository;

import com.classroom.cse_a_classroom.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
}
