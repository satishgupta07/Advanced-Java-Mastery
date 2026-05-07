package com.learning.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

/*
 * Step 3: HttpSession — keeping state across requests.
 *
 * HTTP is stateless. Without a session, every page-refresh would look
 * brand new. The container solves this by:
 *
 *   1. Setting a JSESSIONID cookie on the first response.
 *   2. Looking that ID up on every subsequent request → returning the same HttpSession.
 *
 * Anything you put().setAttribute() on the session is keyed by that
 * cookie, so it survives page refreshes for that one browser.
 *
 * Try this:
 *   - Visit /counter         → "1"
 *   - Refresh                → "2", "3", ...
 *   - Open in incognito      → "1" again (different session)
 */
@WebServlet("/counter")
public class Step3_SessionServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // request.getSession() — returns existing session or creates a new one.
        // request.getSession(false) — returns null if no session exists yet.
        HttpSession session = request.getSession();

        Integer count = (Integer) session.getAttribute("count");
        if (count == null) count = 0;
        count++;
        session.setAttribute("count", count);

        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.printf("<h1>Visit #%d</h1>%n", count);
        out.printf("<p>Session id: %s</p>%n", session.getId());
        out.println("<p>Refresh to increment. Open in incognito to see a separate counter.</p>");
    }
}
