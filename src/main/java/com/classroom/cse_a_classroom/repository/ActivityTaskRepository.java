package com.classroom.cse_a_classroom.repository;

import com.classroom.cse_a_classroom.model.ActivityTask;
import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityTaskRepository extends JpaRepository<ActivityTask, Long> {
    List<ActivityTask> findByUserOrderByDeadlineAsc(User user);
    List<ActivityTask> findByClassroomAndGlobalTrueOrderByDeadlineAsc(Classroom classroom);
    List<ActivityTask> findByUserAndGlobalFalseOrderByDeadlineAsc(User user);
}
