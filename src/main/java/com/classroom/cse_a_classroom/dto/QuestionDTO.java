package com.classroom.cse_a_classroom.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuestionDTO {
    private String text;
    private List<String> options;
    private String correctAnswer;
}
