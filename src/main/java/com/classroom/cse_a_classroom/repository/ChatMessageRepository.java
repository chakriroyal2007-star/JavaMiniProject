package com.classroom.cse_a_classroom.repository;

import com.classroom.cse_a_classroom.model.ChatMessage;
import com.classroom.cse_a_classroom.model.Classroom;
import com.classroom.cse_a_classroom.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    
    // Group messages
    List<ChatMessage> findByClassroomOrderBySentAtAsc(Classroom classroom);
    
    // Personal messages (A to B or B to A)
    @Query("SELECT m FROM ChatMessage m WHERE (m.sender = ?1 AND m.receiver = ?2) OR (m.sender = ?2 AND m.receiver = ?1) ORDER BY m.sentAt ASC")
    List<ChatMessage> findPersonalMessages(User user1, User user2);

    // Get last message for chat list
    ChatMessage findFirstByClassroomOrderBySentAtDesc(Classroom classroom);
}
