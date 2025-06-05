package com.expense.ui;

import com.expense.dao.UserDAO;
import com.expense.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn;

    public LoginFrame() {
        setTitle("Login - Personal Expense Tracker");
        setSize(460, 460);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // === Main Panel ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // === Title ===
        JLabel titleLabel = new JLabel("Welcome" ,JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(new Color(40, 40, 120));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // === Form Panel ===
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 60, 30, 60));
        formPanel.setBackground(Color.WHITE);

        formPanel.add(createLabeledField("Email Address", emailField = new JTextField()));
        formPanel.add(Box.createVerticalStrut(15));
        formPanel.add(createLabeledField("Password", passwordField = new JPasswordField()));
        formPanel.add(Box.createVerticalStrut(25));

        // Buttons
        loginBtn = createPrimaryButton("Login");
        registerBtn = createFlatButton("Don't have an account? Register");

        formPanel.add(loginBtn);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(registerBtn);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        add(mainPanel);

        // === Actions ===
        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.");
                return;
            }

            UserDAO dao = new UserDAO();
            User user = dao.login(email, password);

            if (user != null) {
                JOptionPane.showMessageDialog(this, "Login Successful!");
                dispose();
                new DashboardFrame(user).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid email or password!");
            }
        });

        registerBtn.addActionListener(e -> {
            dispose();
            new RegisterFrame().setVisible(true);
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
