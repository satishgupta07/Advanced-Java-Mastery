package com.learning.jdbcadv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/*
 * Transactions = "all of these or none of them".
 *
 * Default JDBC behaviour is auto-commit: every executeUpdate() is its
 * own transaction. That is fine for one-off statements, dangerous when
 * two updates must succeed together — like "deduct stock + create order".
 *
 * To group them:
 *
 *     connection.setAutoCommit(false);   // open a transaction
 *     ... run several DML statements ...
 *     connection.commit();               // make changes permanent
 *
 * If anything throws:
 *
 *     connection.rollback();             // undo everything since setAutoCommit(false)
 *
 * The classic example below:
 *   - Decrement product stock
 *   - Insert an order row
 *
 * Either both happen or neither does. No half-states.
 */
public class Step3_Transactions {

    public static void main(String[] args) {
        long userId    = 1L;
        long productId = 1L;
        int  quantity  = 1;

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    Step1_PreparedStatement.URL,
                    Step1_PreparedStatement.USER,
                    Step1_PreparedStatement.PASS);

            connection.setAutoCommit(false);   // start tx

            try (PreparedStatement decrementStock = connection.prepareStatement(
                     "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?");
                 PreparedStatement insertOrder = connection.prepareStatement(
                     "INSERT INTO orders (user_id, product_id, quantity) VALUES (?, ?, ?)")) {

                decrementStock.setInt(1, quantity);
                decrementStock.setLong(2, productId);
                decrementStock.setInt(3, quantity);
                int updated = decrementStock.executeUpdate();
                if (updated == 0) {
                    // No row matched — out of stock. Abort the whole transaction.
                    throw new SQLException("Insufficient stock for product " + productId);
                }

                insertOrder.setLong(1, userId);
                insertOrder.setLong(2, productId);
                insertOrder.setInt(3, quantity);
                insertOrder.executeUpdate();
            }

            connection.commit();               // both succeeded — persist
            System.out.println("Order placed.");

        } catch (SQLException e) {
            System.err.println("Order failed, rolling back: " + e.getMessage());
            if (connection != null) {
                try { connection.rollback(); } catch (SQLException ignore) { }
            }
        } finally {
            if (connection != null) {
                try { connection.close(); } catch (SQLException ignore) { }
            }
        }
    }
}
