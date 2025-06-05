package com.expense.ui;

import com.expense.model.User;

import javax.swing.*;
import java.awt.*;

public class DashboardFrame extends JFrame {
    private User currentUser;

    public DashboardFrame(User user) {
        this.currentUser = user;

        setTitle("Personal Expense Tracker - Dashboard");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // === Main Background Panel ===
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(245, 250, 255)); // Soft background

        // === Top Welcome Banner ===
        JLabel titleLabel = new JLabel("Welcome, " + user.getName(), JLabel.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titleLabel.setForeground(new Color(33, 111, 180));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(30, 10, 10, 10));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // === Center Card-style Buttons ===
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        buttonPanel.setBackground(new Color(245, 250, 255));

        buttonPanel.add(createDashboardCard("Add Expense", new Color(0, 153, 204), e -> new AddExpenseFrame(currentUser)));
        buttonPanel.add(createDashboardCard("View Expenses", new Color(51, 153, 102), e -> new ViewExpenseFrame(currentUser)));
        buttonPanel.add(createDashboardCard("Monthly Report", new Color(255, 153, 51), e -> new MonthlyReportFrame(currentUser)));
        buttonPanel.add(createDashboardCard("View Chart", new Color(153, 102, 255), e -> new ExpenseChartFrame(currentUser)));

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        // === Bottom Logout Button ===
        JButton logoutBtn = new JButton("Logout");
        logoutBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        logoutBtn.setBackground(new Color(220, 53, 69)); // Red
        logoutBtn.setForeground(Color.WHITE);
        logoutBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        logoutBtn.setFocusPainted(false);
        logoutBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel logoutPanel = new JPanel();
        logoutPanel.setBackground(new Color(245, 250, 255));
        logoutPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));
        logoutPanel.add(logoutBtn);

        logoutBtn.addActionListener(e -> {
            dispose();
            new LoginFrame().setVisible(true);
        });

        mainPanel.add(logoutPanel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createDashboardCard(String title, Color color, java.awt.event.ActionListener action) {
        JButton button = new JButton(title);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(action);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        panel.add(button, BorderLayout.CENTER);
        return panel;
    }
}
