package com.expense.ui;

import com.expense.dao.ExpenseDAO;
import com.expense.model.Expense;
import com.expense.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.util.List;

public class ViewExpenseFrame extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;
    private User currentUser;

    public ViewExpenseFrame(User user) {
        this.currentUser = user;

        setTitle("My Expenses");
        setSize(800, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // === Header ===
        JLabel headerLabel = new JLabel("All Expenses", JLabel.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        headerLabel.setForeground(new Color(40, 40, 120));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(headerLabel, BorderLayout.NORTH);

        // === Table Columns ===
        String[] columns = {"ID", "Category", "Amount (৳)", "Description", "Date"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(28);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(220, 220, 220));

        // === Alternate Row Coloring (Zebra Style) ===
        table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tbl, Object val, boolean isSelected,
                                                           boolean hasFocus, int row, int col) {
                Component c = super.getTableCellRendererComponent(tbl, val, isSelected, hasFocus, row, col);
                if (isSelected) {
                    c.setBackground(new Color(102, 178, 255));
                    c.setForeground(Color.BLACK);
                } else {
                    c.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE);
                    c.setForeground(Color.BLACK);
                }
                setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
                return c;
            }
        });

        // === Table Header Styling ===
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(65, 105, 225));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 35));

        // === ScrollPane ===
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(scrollPane, BorderLayout.CENTER);

        // === Load Data ===
        loadExpenses();

        setVisible(true);
    }

    private void loadExpenses() {
        ExpenseDAO dao = new ExpenseDAO();
        List<Expense> expenseList = dao.getExpensesByUserId(currentUser.getId());

        for (Expense e : expenseList) {
            Object[] row = {
                    e.getId(),
                    e.getCategory(),
                    String.format("৳ %.2f", e.getAmount()),
                    e.getDescription(),
                    e.getExpenseDate()
            };
            tableModel.addRow(row);
        }
    }
}
