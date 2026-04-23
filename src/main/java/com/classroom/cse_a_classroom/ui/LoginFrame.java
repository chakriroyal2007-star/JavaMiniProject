package com.classroom.cse_a_classroom.ui;

import com.classroom.cse_a_classroom.model.User;
import com.classroom.cse_a_classroom.service.AuthService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("CSE A Classroom - Login");
        setSize(450, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(248, 250, 252));
        setLayout(new GridBagLayout());

        JPanel card = new JPanel();
        card.setPreferredSize(new Dimension(380, 500));
        card.setBackground(Color.WHITE);
        card.putClientProperty("FlatLaf.style", "arc: 20");
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setBorder(BorderFactory.createEmptyBorder(50, 40, 50, 40));

        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setForeground(new Color(79, 70, 229));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(40));

        emailField = new JTextField();
        emailField.setMaximumSize(new Dimension(300, 45));
        passwordField = new JPasswordField();
        passwordField.setMaximumSize(new Dimension(300, 45));

        JButton loginBtn = new JButton("Sign In");
        loginBtn.setBackground(new Color(79, 70, 229));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        loginBtn.setMaximumSize(new Dimension(300, 45));
        loginBtn.addActionListener(this::handleLogin);

        JButton regBtn = new JButton("Create New Account");
        regBtn.setBorderPainted(false);
        regBtn.setContentAreaFilled(false);
        regBtn.setForeground(new Color(79, 70, 229));
        regBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        regBtn.addActionListener(e -> new RegisterDialog(this).setVisible(true));

        card.add(new JLabel("Email Address")); card.add(emailField);
        card.add(Box.createVerticalStrut(20));
        card.add(new JLabel("Password")); card.add(passwordField);
        card.add(Box.createVerticalStrut(30));
        card.add(loginBtn); card.add(Box.createVerticalStrut(20));
        card.add(regBtn);

        add(card);
    }

    private void handleLogin(ActionEvent e) {
        try {
            User u = SpringContext.getBean(AuthService.class).login(emailField.getText(), new String(passwordField.getPassword()));
            dispose(); new DashboardFrame(u).setVisible(true);
        } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
    }
}
