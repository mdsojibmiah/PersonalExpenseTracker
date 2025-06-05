package com.expense.ui;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;
import com.expense.model.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddExpenseFrame extends JFrame {
    private JComboBox<String> categoryBox;
    private JTextField amountField, dateField;
    private JTextArea descArea;
    private JButton addBtn;
    private User currentUser;

    public AddExpenseFrame(User user) {
        this.currentUser = user;

        setTitle("Add Expense");
        setSize(450, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(245, 250, 255));
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        JLabel heading = new JLabel("Add New Expense");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 22));
        heading.setAlignmentX(Component.CENTER_ALIGNMENT);
        heading.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        heading.setForeground(new Color(40, 40, 40));

        categoryBox = new JComboBox<>(new String[]{"Food", "Transport", "Entertainment", "Shopping", "Other"});
        categoryBox.setEditable(true);

        amountField = new JTextField();
        descArea = new JTextArea(3, 20);
        dateField = new JTextField(LocalDate.now().toString());

        addBtn = new JButton("Save Expense");
        addBtn.setFont(new Font("Segoe UI", Font.BOLD, 15));
        addBtn.setBackground(new Color(70, 130, 180));
        addBtn.setForeground(Color.WHITE);
        addBtn.setFocusPainted(false);
        addBtn.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        addBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        addBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Form layout
        mainPanel.add(heading);
        mainPanel.add(createField("Category", categoryBox));
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createField("Amount (৳)", amountField));
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createTextArea("Description", descArea));
        mainPanel.add(Box.createVerticalStrut(15));
        mainPanel.add(createField("Date (YYYY-MM-DD)", dateField));
        mainPanel.add(Box.createVerticalStrut(30));
        mainPanel.add(addBtn);

        add(mainPanel);

        // Action listener
        addBtn.addActionListener(e -> {
            try {
                String category = categoryBox.getSelectedItem().toString().trim();
                double amount = Double.parseDouble(amountField.getText().trim());
                String description = descArea.getText().trim();
                String date = dateField.getText().trim();

                if (category.isEmpty() || amount <= 0 || description.isEmpty() || date.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields properly!");
                    return;
                }

                Expense expense = new Expense();
                expense.setUserId(currentUser.getId());
                expense.setCategory(category);
                expense.setAmount(amount);
                expense.setDescription(description);
                expense.setExpenseDate(date);

                boolean success = new ExpenseDAO().addExpense(expense);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Expense added successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to add expense.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Invalid input!");
            }
        });

        setVisible(true);
    }

    private JPanel createField(String labelText, JTextField field) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));

        field.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        panel.add(label, BorderLayout.NORTH);
        panel.add(field, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createField(String labelText, JComboBox<String> comboBox) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));

        comboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        comboBox.setBackground(Color.WHITE);
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200)));

        panel.add(label, BorderLayout.NORTH);
        panel.add(comboBox, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createTextArea(String labelText, JTextArea area) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout(5, 5));
        panel.setBackground(Color.WHITE);

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(new Color(60, 60, 60));

        area.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        area.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        area.setLineWrap(true);
        area.setWrapStyleWord(true);

        JScrollPane scroll = new JScrollPane(area);
        scroll.setBorder(BorderFactory.createEmptyBorder());

        panel.add(label, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);
        return panel;
    }
}
