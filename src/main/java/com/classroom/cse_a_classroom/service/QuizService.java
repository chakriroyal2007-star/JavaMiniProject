package com.classroom.cse_a_classroom.service;

import com.classroom.cse_a_classroom.dto.QuizDTO;
import com.classroom.cse_a_classroom.model.*;
import com.classroom.cse_a_classroom.repository.QuizRepository;
import com.classroom.cse_a_classroom.repository.SubmissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    public Quiz createQuiz(QuizDTO quizDTO, User teacher, Classroom classroom) {
        Quiz quiz = Quiz.builder()
                .title(quizDTO.getTitle())
                .topic(quizDTO.getTopic())
                .difficulty(quizDTO.getDifficulty())
                .password(quizDTO.getPassword())
                .teacher(teacher)
                .classroom(classroom)
                .build();

        List<Question> questions = quizDTO.getQuestions().stream().map(qDto -> 
            Question.builder()
                    .text(qDto.getText())
                    .options(qDto.getOptions())
                    .correctAnswer(qDto.getCorrectAnswer())
                    .quiz(quiz)
                    .build()
        ).collect(Collectors.toList());

        quiz.setQuestions(questions);
        return quizRepository.save(quiz);
    }

    public List<Quiz> getClassroomQuizzes(Classroom classroom) {
        return quizRepository.findByClassroom(classroom);
    }

    public Quiz getQuizById(Long id) {
        return quizRepository.findById(id).orElseThrow(() -> new RuntimeException("Quiz not found"));
    }

    public boolean validatePassword(Long quizId, String password) {
        Quiz quiz = getQuizById(quizId);
        return quiz.getPassword().equals(password);
    }

    public Submission submitQuiz(Long quizId, User student, Map<Long, String> answers) {
        if (submissionRepository.existsByStudentAndQuiz(student, getQuizById(quizId))) {
            throw new RuntimeException("You have already submitted this quiz");
        }

        Quiz quiz = getQuizById(quizId);
        int score = 0;
        int correct = 0;
        int wrong = 0;
        for (Question question : quiz.getQuestions()) {
            String studentAnswer = answers.get(question.getId());
            if (studentAnswer != null && studentAnswer.equals(question.getCorrectAnswer())) {
                score++;
                correct++;
            } else {
                wrong++;
            }
        }

        Submission submission = Submission.builder()
                .student(student)
                .quiz(quiz)
                .score(score)
                .totalQuestions(quiz.getQuestions().size())
                .correctAnswers(correct)
                .wrongAnswers(wrong)
                .build();

        return submissionRepository.save(submission);
    }

    public List<Submission> getQuizSubmissions(Long quizId) {
        return submissionRepository.findByQuiz(getQuizById(quizId));
    }

    public void deleteQuiz(Long id) {
        quizRepository.deleteById(id);
    }
}
