package com.expense.dao;

import com.expense.model.Expense;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ExpenseDAO {

    public boolean addExpense(Expense expense) {
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "INSERT INTO expenses (user_id, category, amount, description, expense_date) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, expense.getUserId());
            pst.setString(2, expense.getCategory());
            pst.setDouble(3, expense.getAmount());
            pst.setString(4, expense.getDescription());
            pst.setString(5, expense.getExpenseDate());

            return pst.executeUpdate() == 1;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // ✅ নতুন মেথড: ইউজার আইডি অনুযায়ী সব খরচ রিটার্ন করে
    public List<Expense> getExpensesByUserId(int userId) {
        List<Expense> list = new ArrayList<>();
        try {
            Connection conn = DBConnection.getConnection();
            String sql = "SELECT * FROM expenses WHERE user_id = ? ORDER BY expense_date DESC";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Expense e = new Expense();
                e.setId(rs.getInt("id"));
                e.setUserId(rs.getInt("user_id"));
                e.setCategory(rs.getString("category"));
                e.setAmount(rs.getDouble("amount"));
                e.setDescription(rs.getString("description"));
                e.setExpenseDate(rs.getString("expense_date"));

                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public Map<String, Double> getMonthlyReport(int userId) {
    Map<String, Double> report = new LinkedHashMap<>();
    try {
        Connection conn = DBConnection.getConnection();
        String sql = "SELECT DATE_FORMAT(expense_date, '%Y-%m') AS month, SUM(amount) AS total " +
                     "FROM expenses WHERE user_id = ? GROUP BY month ORDER BY month DESC";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, userId);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            report.put(rs.getString("month"), rs.getDouble("total"));
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return report;
}

}
