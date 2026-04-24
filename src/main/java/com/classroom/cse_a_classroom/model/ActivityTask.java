package com.classroom.cse_a_classroom.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "activity_tasks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ActivityTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Builder.Default
    private boolean completed = false;

    @Column(name = "is_global")
    @Builder.Default
    private boolean global = false; // True if created by teacher for everyone

    private LocalDateTime deadline;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // Owner of the task (or creator if global)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id")
    private Classroom classroom; // Classroom this task belongs to

    @Column(name = "xp_reward")
    @Builder.Default
    private int xpReward = 50;
}
