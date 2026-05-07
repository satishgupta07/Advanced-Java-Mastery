package com.learning.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Step 5: a Servlet backed by the database.
 *
 * Conceptually identical to Phase 2's UserDAO — the SQL lives in one
 * place, the Servlet stays focused on HTTP concerns. In a real
 * project you'd extract the DAO into its own class (and inject it via
 * init() or the listener) — inlined here so the whole flow fits in
 * one file you can read top-to-bottom.
 *
 * Notice the smell: building HTML by string concatenation hurts.
 * That is exactly the problem JSP was invented to solve — Phase 4.
 */
@WebServlet("/users")
public class Step5_ServletWithUserDAO extends HttpServlet {

    private static final String URL  = "jdbc:mysql://localhost:3306/learning_db";
    private static final String USER = "learner";
    private static final String PASS = "learner_pw";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        out.println("<h1>Users</h1>");
        out.println("<table border='1' cellpadding='6'>");
        out.println("<tr><th>id</th><th>name</th><th>email</th></tr>");

        for (UserRow u : findAll()) {
            out.printf("<tr><td>%d</td><td>%s</td><td>%s</td></tr>%n",
                u.id, u.name, u.email);
        }
        out.println("</table>");
        out.println("<p><a href='index.html'>Back</a></p>");
    }

    // ---- DAO-style helper, kept inline for readability ----
    private record UserRow(long id, String name, String email) {}

    private List<UserRow> findAll() {
        List<UserRow> out = new ArrayList<>();
        String sql = "SELECT id, name, email FROM users ORDER BY id";
        try (Connection c = DriverManager.getConnection(URL, USER, PASS);
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new UserRow(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException("findAll failed", e);
        }
        return out;
    }
}
