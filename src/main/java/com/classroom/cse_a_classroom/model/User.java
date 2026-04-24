package com.classroom.cse_a_classroom.model;

import jakarta.persistence.*;
import lombok.*;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    @Builder.Default
    @Column(columnDefinition = "integer default 0", nullable = false)
    private int xp = 0;

    @Builder.Default
    @Column(columnDefinition = "integer default 1", nullable = false)
    private int level = 1;

    private String title; // e.g., "Novice", "Scholar", "Quiz Master"
}
