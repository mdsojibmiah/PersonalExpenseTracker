package com.expense.dao;
import java.sql.*;

public class DBConnection {
    private static Connection conn;

    public static Connection getConnection() {
        try {
            if (conn == null) {
                // For MySQL
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/expense_tracker", "root", ""
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conn;
    }
}
