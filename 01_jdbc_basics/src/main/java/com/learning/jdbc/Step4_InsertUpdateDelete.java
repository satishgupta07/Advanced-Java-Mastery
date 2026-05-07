package com.learning.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Step 4 of JDBC: executeUpdate for INSERT, UPDATE, DELETE.
 *
 * Two execute methods you must distinguish:
 *
 *     executeQuery(sql)   → returns ResultSet  (use for SELECT)
 *     executeUpdate(sql)  → returns int        (rows affected; use for INSERT/UPDATE/DELETE/DDL)
 *
 * There is also execute(sql) that returns boolean — useful when
 * you do not know what kind of statement you are running. Avoid
 * unless you actually need it.
 *
 * IMPORTANT: this class uses raw Statement on purpose, to mirror
 * what Phase 2 will replace with PreparedStatement. Building SQL
 * via string concatenation like this is a SQL-injection bug magnet.
 * In any real codebase, use PreparedStatement.
 */
public class Step4_InsertUpdateDelete {

    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection(
                Step2_GetConnection.URL,
                Step2_GetConnection.USER,
                Step2_GetConnection.PASSWORD);
             Statement statement = connection.createStatement()) {

            // ---- INSERT ----
            int inserted = statement.executeUpdate(
                "INSERT INTO users (name, email) VALUES ('Dave Wilson', 'dave@example.com')"
            );
            System.out.println("Rows inserted: " + inserted);   // 1

            // ---- UPDATE ----
            int updated = statement.executeUpdate(
                "UPDATE users SET name = 'David Wilson' WHERE email = 'dave@example.com'"
            );
            System.out.println("Rows updated: " + updated);     // 1

            // ---- DELETE ----
            int deleted = statement.executeUpdate(
                "DELETE FROM users WHERE email = 'dave@example.com'"
            );
            System.out.println("Rows deleted: " + deleted);     // 1

        } catch (SQLException e) {
            System.err.println("DML failed: " + e.getMessage());
        }
    }
}
