package com.learning.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/*
 * Step 2: handling a form POST.
 *
 *   - GET  is for fetching      (idempotent, safe, parameters in URL)
 *   - POST is for submitting    (state-changing, parameters in body)
 *
 * index.html has a <form action="register" method="post">. When the
 * user submits, Tomcat parses the form-encoded body and exposes each
 * field via request.getParameter(name).
 *
 * IMPORTANT — return null when a parameter is missing. Always check
 * before using, or wrap with Optional / Objects.requireNonNullElse.
 */
@WebServlet("/register")
public class Step2_FormHandlingServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String name  = request.getParameter("name");    // null if not in form
        String email = request.getParameter("email");

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("<p>Both name and email are required.</p>");
            return;
        }

        out.println("<h1>Registered</h1>");
        out.println("<p>Name : " + escape(name)  + "</p>");
        out.println("<p>Email: " + escape(email) + "</p>");
        out.println("<a href='index.html'>Back</a>");
    }

    // Always escape user input before rendering — prevents XSS.
    // Frameworks (JSP EL, Thymeleaf, Spring MVC) do this for you.
    private static String escape(String s) {
        return s.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
