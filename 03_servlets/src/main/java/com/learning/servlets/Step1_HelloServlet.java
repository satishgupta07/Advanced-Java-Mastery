package com.learning.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/*
 * Step 1: the simplest possible Servlet.
 *
 * @WebServlet("/hello") replaces the old <servlet-mapping> XML.
 * Visit http://localhost:8080/servlets/hello to invoke doGet().
 *
 * doGet() signature is fixed by HttpServlet:
 *     - HttpServletRequest  : everything about the incoming request (params, headers, cookies)
 *     - HttpServletResponse : everything about the outgoing response (status, headers, body)
 *
 * The body is written through response.getWriter() (a PrintWriter).
 * Setting Content-Type BEFORE writing is important — once the first
 * byte goes out, headers are committed and cannot be changed.
 */
@WebServlet("/hello")
public class Step1_HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html><body>");
        out.println("<h1>Hello from a Servlet</h1>");
        out.println("<p>Method: "      + request.getMethod()    + "</p>");
        out.println("<p>Path: "        + request.getRequestURI() + "</p>");
        out.println("<p>Query: "       + request.getQueryString() + "</p>");
        out.println("</body></html>");
    }
}
