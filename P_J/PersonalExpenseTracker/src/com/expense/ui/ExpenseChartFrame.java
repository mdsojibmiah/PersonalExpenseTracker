package com.expense.ui;

import com.expense.dao.DBConnection;
import com.expense.model.User;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ExpenseChartFrame extends JFrame {

    public ExpenseChartFrame(User user) {
        setTitle("Expense Chart - " + user.getName());
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Header Panel ===
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(33, 150, 243));
        JLabel heading = new JLabel("Your Expense Distribution by Category");
        heading.setFont(new Font("Segoe UI", Font.BOLD, 20));
        heading.setForeground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 10, 15, 10));
        headerPanel.add(heading);
        add(headerPanel, BorderLayout.NORTH);

        // === Dataset Creation ===
        DefaultPieDataset dataset = new DefaultPieDataset();

        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT category, SUM(amount) AS total FROM expenses WHERE user_id = ? GROUP BY category";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, user.getId());
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                dataset.setValue(rs.getString("category"), rs.getDouble("total"));
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error loading chart data");
            return;
        }

        // === Chart Configuration ===
        JFreeChart chart = ChartFactory.createPieChart(
                "", // Title handled by custom header
                dataset,
                true,
                true,
                false
        );

        // === Pie Plot Customization ===
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setSectionOutlinesVisible(false);
        plot.setLabelFont(new Font("Segoe UI", Font.PLAIN, 14));
        plot.setBackgroundPaint(new Color(245, 250, 255));
        plot.setCircular(true);

        // === Chart Panel ===
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setBackground(new Color(245, 250, 255));
        add(chartPanel, BorderLayout.CENTER);

        setVisible(true);
    }
}
