package com.classroom.cse_a_classroom.ui;

import com.classroom.cse_a_classroom.model.*;
import com.classroom.cse_a_classroom.service.QuizService;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class QuizAttemptFrame extends JFrame {
    private Quiz quiz;
    private User student;
    private Map<Long, String> selectedAnswers = new HashMap<>();

    public QuizAttemptFrame(Quiz quiz, User student) {
        this.quiz = quiz;
        this.student = student;
        setTitle("Attempting: " + quiz.getTitle());
        setSize(600, 700);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JPanel p = new JPanel(); p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        for(Question q : quiz.getQuestions()) {
            JPanel qp = new JPanel(); qp.setLayout(new BoxLayout(qp, BoxLayout.Y_AXIS));
            qp.setBorder(BorderFactory.createTitledBorder(q.getText()));
            ButtonGroup bg = new ButtonGroup();
            for(String opt : q.getOptions()) {
                JRadioButton rb = new JRadioButton(opt);
                rb.addActionListener(e -> selectedAnswers.put(q.getId(), opt));
                bg.add(rb); qp.add(rb);
            }
            p.add(qp);
        }

        JButton sub = new JButton("Submit");
        sub.addActionListener(e -> {
            try {
                Submission s = SpringContext.getBean(QuizService.class).submitQuiz(quiz.getId(), student, selectedAnswers, 0);
                JOptionPane.showMessageDialog(this, "Score: " + s.getScore() + "/" + s.getTotalQuestions() + " (" + s.getPercentage() + "%)"); 
                dispose();
            } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
        });

        add(new JScrollPane(p), BorderLayout.CENTER);
        add(sub, BorderLayout.SOUTH);
    }
}
