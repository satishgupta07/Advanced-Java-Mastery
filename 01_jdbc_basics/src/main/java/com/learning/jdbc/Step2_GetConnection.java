package com.learning.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/*
 * Step 2 of JDBC: Get a Connection.
 *
 * A Connection is an open conversation with the database. It owns
 * a TCP socket, an authenticated session, and (depending on isolation
 * level) a transaction. Opening one is expensive — that is why later
 * phases use connection pools.
 *
 * The JDBC URL has a strict shape:
 *
 *     jdbc : mysql  : // host : port / database
 *      ↑      ↑       ↑       ↑      ↑
 *   protocol  driver  host    port   schema
 *
 * Optional query params come after the schema:
 *     ?useSSL=false&serverTimezone=UTC
 */
public class Step2_GetConnection {

    // Centralized so every step in this folder uses the same coordinates.
    static final String URL      = "jdbc:mysql://localhost:3306/learning_db";
    static final String USER     = "learner";
    static final String PASSWORD = "learner_pw";

    public static void main(String[] args) {
        // DriverManager picks the right registered driver based on the URL prefix.
        // No Class.forName() needed — JDBC 4 auto-discovers drivers.
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // A live Connection prints something like:
            //   com.mysql.cj.jdbc.ConnectionImpl@7c30a502
            System.out.println("Connected to: " + connection.getCatalog());
            System.out.println("Connection object: " + connection);
        } catch (SQLException e) {
            // Most common causes:
            //   - MySQL server not running
            //   - Wrong username / password
            //   - Database 'learning_db' does not exist (run 04_sample_schema.sql)
            System.err.println("Connection failed: " + e.getMessage());
        }
    }
}
