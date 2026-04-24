package com.classroom.cse_a_classroom.service;

import com.classroom.cse_a_classroom.model.ActivityTask;
import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.repository.ActivityTaskRepository;
import com.classroom.cse_a_classroom.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ActivityTaskService {

    @Autowired
    private ActivityTaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public ActivityTask createTask(String title, String description, LocalDateTime deadline, boolean isGlobal, User creator, Classroom classroom) {
        ActivityTask task = ActivityTask.builder()
                .title(title)
                .description(description)
                .deadline(deadline)
                .global(isGlobal)
                .user(creator)
                .classroom(classroom)
                .xpReward(isGlobal ? 50 : 10) // More XP for teacher tasks
                .build();
        return taskRepository.save(task);
    }

    public List<ActivityTask> getPersonalTasks(User user) {
        return taskRepository.findByUserAndGlobalFalseOrderByDeadlineAsc(user);
    }

    public List<ActivityTask> getClassroomTasks(Classroom classroom) {
        return taskRepository.findByClassroomAndGlobalTrueOrderByDeadlineAsc(classroom);
    }

    @Transactional
    public ActivityTask completeTask(Long taskId, User user) {
        ActivityTask task = taskRepository.findById(taskId)
                .orElseThrow(() -> new RuntimeException("Task not found"));
        
        if (task.isCompleted()) {
            return task;
        }

        task.setCompleted(true);
        
        // Reward XP
        user.setXp(user.getXp() + task.getXpReward());
        user.setLevel((user.getXp() / 1000) + 1);
        
        // Update Title logic (simplified, can be more complex)
        if(user.getLevel() >= 10) user.setTitle("Task Grandmaster");
        else if(user.getLevel() >= 5) user.setTitle("Productive Scholar");
        else if(user.getLevel() >= 2) user.setTitle("Active Learner");
        
        userRepository.save(user);
        return taskRepository.save(task);
    }

    public void deleteTask(Long taskId) {
        taskRepository.deleteById(taskId);
    }
}
