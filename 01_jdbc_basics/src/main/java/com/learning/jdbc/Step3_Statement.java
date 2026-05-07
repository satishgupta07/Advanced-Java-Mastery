package com.learning.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Step 3 of JDBC: Statement + executeQuery + ResultSet.
 *
 * Statement is the simplest way to send SQL to the database.
 * It works fine for static queries, but is UNSAFE for any SQL
 * that includes user input — that is what PreparedStatement
 * (Phase 2) was invented for.
 *
 * A ResultSet is a cursor — it points BEFORE the first row when
 * returned. You call .next() to advance; it returns false when
 * there are no more rows.
 *
 *     while (rs.next()) {
 *         rs.getLong("id");      // read column by name OR index
 *     }
 */
public class Step3_Statement {

    public static void main(String[] args) {
        String sql = "SELECT id, name, email FROM users";

        try (Connection connection = DriverManager.getConnection(
                Step2_GetConnection.URL,
                Step2_GetConnection.USER,
                Step2_GetConnection.PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {

            System.out.println("--- users ---");
            while (rs.next()) {
                long id      = rs.getLong("id");
                String name  = rs.getString("name");
                String email = rs.getString("email");
                System.out.printf("%d | %-15s | %s%n", id, name, email);
            }

        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
        }
    }
}
