package com.learning.jdbcadv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/*
 * PreparedStatement = Statement with parameter slots.
 *
 *   String sql = "SELECT * FROM users WHERE email = ?";
 *                                                    ↑
 *                                                    placeholder, NOT string concat
 *
 *   ps.setString(1, "alice@example.com");
 *
 * Two reasons every line of production JDBC uses this:
 *
 *   SECURITY: the driver sends SQL and parameters separately to the
 *             server. There is no point at which user input becomes
 *             part of the SQL text. SQL injection becomes structurally
 *             impossible, not "filtered out".
 *
 *   PERFORMANCE: the server parses + plans the SQL once. The next
 *                ten thousand calls reuse the cached plan and only
 *                ship the parameter values.
 *
 * Indices are 1-based, not 0-based — a JDBC quirk you will trip on
 * exactly once and then never again.
 */
public class Step1_PreparedStatement {

    static final String URL  = "jdbc:mysql://localhost:3306/learning_db";
    static final String USER = "learner";
    static final String PASS = "learner_pw";

    public static void main(String[] args) {
        // Pretend this came from a web form. Including the apostrophe
        // would have crashed (or worse) a Statement-based query.
        String emailFilter = "alice@example.com";

        String sql = "SELECT id, name, email FROM users WHERE email = ?";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, emailFilter);   // bind parameter #1

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    System.out.printf("%d | %s | %s%n",
                        rs.getLong("id"),
                        rs.getString("name"),
                        rs.getString("email"));
                }
            }

        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
        }
    }
}
