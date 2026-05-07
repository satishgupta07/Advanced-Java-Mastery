package com.learning.jsp;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/*
 * Step 4: Model 2 MVC.
 *
 *   Servlet (Controller) → loads users, stuffs them on the request, forwards.
 *   JSP    (View)        → reads ${users}, renders HTML. No SQL. No Java code.
 *
 * forward() vs sendRedirect() — important distinction:
 *   - forward()      : server-side hand-off. Same request, same URL in browser.
 *                      The JSP can read the attributes you set.
 *   - sendRedirect() : tells the browser to make a NEW request to a new URL.
 *                      A new request means request.setAttribute() data is gone.
 *
 * For "controller picks data → view renders", you want forward().
 *
 * The JSP lives under WEB-INF/views/ on purpose. Anything under WEB-INF
 * is NOT directly reachable from the browser, so users.jsp can ONLY be
 * served via this controller — exactly what we want.
 */
@WebServlet("/users")
public class UserListServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Model — load data
        List<User> users = findAllUsers();

        // Stuff into request scope for the view to read
        request.setAttribute("users", users);

        // Forward to the JSP view (server-side hand-off, same request)
        RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/users.jsp");
        rd.forward(request, response);
    }

    // ---- Model ----------------------------------------------------------

    public static class User {
        private final long id;
        private final String name;
        private final String email;
        public User(long id, String name, String email) {
            this.id = id; this.name = name; this.email = email;
        }
        // EL calls these getters: ${u.name} → u.getName()
        public long getId()       { return id; }
        public String getName()   { return name; }
        public String getEmail()  { return email; }
    }

    private List<User> findAllUsers() {
        List<User> out = new ArrayList<>();
        String sql = "SELECT id, name, email FROM users ORDER BY id";
        try (Connection c = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/learning_db", "learner", "learner_pw");
             PreparedStatement ps = c.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                out.add(new User(
                    rs.getLong("id"),
                    rs.getString("name"),
                    rs.getString("email")));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return out;
    }
}
