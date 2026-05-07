package com.learning.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*
 * Step 5 of JDBC: Close resources properly.
 *
 * Every JDBC object (Connection, Statement, ResultSet) holds a
 * native resource. Forgetting to close them leaks:
 *   - TCP sockets        (the OS will eventually run out)
 *   - DB-side memory     (cursors, transactions stay open)
 *   - Pool slots         (the next request waits forever)
 *
 * BEFORE Java 7 — manual try / finally with null checks (verbose, easy to get wrong):
 *
 *     Connection c = null;
 *     Statement  s = null;
 *     ResultSet  r = null;
 *     try {
 *         c = ...; s = ...; r = ...;
 *     } finally {
 *         if (r != null) r.close();
 *         if (s != null) s.close();
 *         if (c != null) c.close();
 *     }
 *
 * AFTER Java 7 — try-with-resources. Auto-closes in reverse order.
 * USE THIS. Always.
 */
public class Step5_CloseResources {

    public static void main(String[] args) {

        // Each resource is declared in the try( ... ) header.
        // When the block exits — normally or via exception — close()
        // is called on each, in REVERSE order of declaration.
        try (Connection connection = DriverManager.getConnection(
                Step2_GetConnection.URL,
                Step2_GetConnection.USER,
                Step2_GetConnection.PASSWORD);
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT COUNT(*) AS n FROM users")) {

            if (rs.next()) {
                System.out.println("User count: " + rs.getInt("n"));
            }
            // No finally block. No null checks. No close() calls.
            // The JVM closes rs, then statement, then connection — in that order.

        } catch (SQLException e) {
            System.err.println("Query failed: " + e.getMessage());
        }
    }
}
