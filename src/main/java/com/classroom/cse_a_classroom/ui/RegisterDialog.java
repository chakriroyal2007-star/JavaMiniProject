package com.classroom.cse_a_classroom.ui;

import com.classroom.cse_a_classroom.dto.AuthRequest;
import com.classroom.cse_a_classroom.model.Role;
import com.classroom.cse_a_classroom.service.AuthService;
import javax.swing.*;
import java.awt.*;

public class RegisterDialog extends JDialog {
    private JTextField nameField, emailField;
    private JPasswordField passField;
    private JComboBox<Role> roleCombo;

    public RegisterDialog(JFrame parent) {
        super(parent, "Create Account", true);
        setSize(350, 450);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.Y_AXIS));
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        nameField = new JTextField(); nameField.setBorder(BorderFactory.createTitledBorder("Full Name"));
        emailField = new JTextField(); emailField.setBorder(BorderFactory.createTitledBorder("Email Address"));
        passField = new JPasswordField(); passField.setBorder(BorderFactory.createTitledBorder("Password"));
        roleCombo = new JComboBox<>(Role.values()); roleCombo.setBorder(BorderFactory.createTitledBorder("I am a..."));

        JButton regBtn = new JButton("Register");
        regBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        regBtn.addActionListener(e -> {
            try {
                AuthRequest req = new AuthRequest();
                req.setName(nameField.getText()); req.setEmail(emailField.getText());
                req.setPassword(new String(passField.getPassword())); req.setRole((Role)roleCombo.getSelectedItem());
                SpringContext.getBean(AuthService.class).register(req);
                JOptionPane.showMessageDialog(this, "Success!"); dispose();
            } catch(Exception ex) { JOptionPane.showMessageDialog(this, ex.getMessage()); }
        });

        p.add(nameField); p.add(emailField); p.add(passField); p.add(roleCombo); p.add(Box.createVerticalStrut(20)); p.add(regBtn);
        add(p);
    }
}
