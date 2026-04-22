package com.classroom.cse_a_classroom.repository;

import com.classroom.cse_a_classroom.model.Resource;
import com.classroom.cse_a_classroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.classroom.cse_a_classroom.model.Classroom;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {
    List<Resource> findByTeacher(User teacher);
    List<Resource> findByClassroom(Classroom classroom);
}
