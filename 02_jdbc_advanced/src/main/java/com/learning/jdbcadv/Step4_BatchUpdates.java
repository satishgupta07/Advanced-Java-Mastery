package com.learning.jdbcadv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * Batch updates = many INSERTs in one round-trip.
 *
 * Naive loop (DON'T):
 *     for (User u : users) {
 *         ps.setString(1, u.getName());
 *         ps.setString(2, u.getEmail());
 *         ps.executeUpdate();          // one network round-trip per row
 *     }
 *
 * Batched (DO):
 *     for (User u : users) {
 *         ps.setString(1, u.getName());
 *         ps.setString(2, u.getEmail());
 *         ps.addBatch();               // queue locally
 *     }
 *     ps.executeBatch();               // ship them all in one round-trip
 *
 * Speedup is dramatic for large bulks — easily 10-100x faster.
 *
 * MySQL extra: append ?rewriteBatchedStatements=true to the JDBC URL
 * to also fold the batch into a single multi-row INSERT statement.
 */
public class Step4_BatchUpdates {

    public static void main(String[] args) {
        String[][] productsToInsert = {
            { "USB-C Hub",         "49.99",  "30" },
            { "Webcam 1080p",      "59.99",  "25" },
            { "Desk Lamp",         "39.99",  "40" },
            { "Standing Desk Mat", "29.99",  "60" }
        };

        String sql = "INSERT INTO products (name, price, stock) VALUES (?, ?, ?)";

        try (Connection c = DriverManager.getConnection(
                Step1_PreparedStatement.URL,
                Step1_PreparedStatement.USER,
                Step1_PreparedStatement.PASS);
             PreparedStatement ps = c.prepareStatement(sql)) {

            for (String[] row : productsToInsert) {
                ps.setString(1, row[0]);
                ps.setBigDecimal(2, new java.math.BigDecimal(row[1]));
                ps.setInt(3, Integer.parseInt(row[2]));
                ps.addBatch();        // queue, do not execute yet
            }

            int[] perRow = ps.executeBatch();   // one network round-trip
            System.out.println("Rows inserted in batch: " + perRow.length);

        } catch (SQLException e) {
            System.err.println("Batch failed: " + e.getMessage());
        }
    }
}
