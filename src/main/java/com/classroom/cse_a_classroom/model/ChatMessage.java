package com.classroom.cse_a_classroom.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @ManyToOne
    @JoinColumn(name = "receiver_id") // Null for group messages
    private User receiver;

    @ManyToOne
    @JoinColumn(name = "classroom_id") // Null for personal messages
    private Classroom classroom;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String fileUrl;
    private String fileName;
    private String fileType;

    private LocalDateTime sentAt;

    @PrePersist
    protected void onCreate() {
        sentAt = LocalDateTime.now();
    }
}
