package com.expense.ui;

import com.expense.dao.ExpenseDAO;
import com.expense.model.User;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Map;

public class MonthlyReportFrame extends JFrame {

    public MonthlyReportFrame(User user) {
        setTitle("Monthly Expense Report");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Title ===
        JLabel title = new JLabel("Monthly Report", JLabel.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 22));
        title.setForeground(new Color(0, 102, 204));
        title.setBorder(BorderFactory.createEmptyBorder(15, 10, 10, 10));
        add(title, BorderLayout.NORTH);

        // === Get Report Data ===
        ExpenseDAO dao = new ExpenseDAO();
        Map<String, Double> report = dao.getMonthlyReport(user.getId());

        // === Table Model ===
        String[] columns = {"Month", "Total Expense (৳)"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (Map.Entry<String, Double> entry : report.entrySet()) {
            model.addRow(new Object[]{entry.getKey(), String.format("৳ %.2f", entry.getValue())});
        }

        // === Table ===
        JTable table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.setRowHeight(28);
        table.setShowHorizontalLines(true);
        table.setGridColor(new Color(220, 220, 220));

        // === Zebra Striping ===
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

        // === Table Header Style ===
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 16));
        header.setBackground(new Color(0, 123, 255));
        header.setForeground(Color.WHITE);
        header.setPreferredSize(new Dimension(100, 35));

        // === ScrollPane ===
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));
        add(scroll, BorderLayout.CENTER);

        setVisible(true);
    }
}
