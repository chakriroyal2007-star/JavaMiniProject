package com.classroom.cse_a_classroom.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuizDTO {
    private String title;
    private String topic;
    private String difficulty;
    private String password;
    private List<QuestionDTO> questions;
}
