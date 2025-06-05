package com.expense.ui;

import com.expense.dao.UserDAO;
import com.expense.model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterFrame extends JFrame {
    private JTextField nameField, emailField;
    private JPasswordField passwordField;
    private JButton registerBtn, loginBtn;

    public RegisterFrame() {
        setTitle("Register - Personal Expense Tracker");
        setSize(460, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // === Main Panel ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // === Title Panel ===
        JLabel titleLabel = new JLabel("Create Your Account", JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(40, 40, 120));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));

        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // === Form Panel ===
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 20, 60));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(createLabeledField("Full Name", nameField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createLabeledField("Email Address", emailField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createLabeledField("Password", passwordField = new JPasswordField()));
        formPanel.add(Box.createVerticalStrut(25));

        // === Buttons ===
        registerBtn = createPrimaryButton("Register");
        loginBtn = createFlatButton("Already have an account? Login");

        formPanel.add(registerBtn);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(loginBtn);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // === Actions ===
        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "All fields are required!");
                return;
            }

            User user = new User(name, email, password);
            UserDAO dao = new UserDAO();
            boolean success = dao.registerUser(user);

            if (success) {
                JOptionPane.showMessageDialog(this, "Registration Successful!");
                dispose();
                new LoginFrame().setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Email already exists or error occurred!");
            }
        });

        loginBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        setVisible(true);
    }

    private JPanel createLabeledField(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(180, 180, 180)),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        field.setBackground(new Color(250, 250, 250));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);

        return panel;
    }

    private JButton createPrimaryButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btn.setBackground(new Color(65, 105, 225));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        return btn;
    }

    private JButton createFlatButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setForeground(new Color(100, 100, 100));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        return btn;
    }
}
