package com.learning.servlets;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

/*
 * Two pieces of cross-cutting machinery the Servlet spec gives you.
 *
 * Filter — runs BEFORE and AFTER every matching request.
 *   Use cases: logging, auth, compression, character encoding, CORS.
 *   The chain.doFilter(...) call passes control to the next filter
 *   (or, eventually, the actual Servlet). Skip that call to short-circuit.
 *
 * Listener — fires on container lifecycle events.
 *   ServletContextListener fires once when the WAR starts and once when
 *   it stops. Open/close shared resources here (connection pools, caches).
 *
 * Both classes are placed in this single file purely for compactness.
 * In real code, one class per file.
 */
public class Step4_FilterAndListener {

    @WebFilter("/*")    // applies to every request in the WAR
    public static class RequestLoggingFilter implements Filter {
        @Override
        public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
                throws IOException, ServletException {

            long start = System.nanoTime();
            HttpServletRequest http = (HttpServletRequest) req;

            // Pre-processing — runs BEFORE the servlet
            System.out.printf("--> %s %s%n", http.getMethod(), http.getRequestURI());

            chain.doFilter(req, res);   // hand off to the next link in the chain

            // Post-processing — runs AFTER the servlet has written its response
            long ms = (System.nanoTime() - start) / 1_000_000;
            System.out.printf("<-- %s %s [%d ms]%n", http.getMethod(), http.getRequestURI(), ms);
        }
    }

    @WebListener
    public static class AppLifecycleListener implements ServletContextListener {
        @Override
        public void contextInitialized(ServletContextEvent sce) {
            // Spot to open the connection pool, warm caches, etc.
            System.out.println("[App] Starting up: " + sce.getServletContext().getContextPath());
        }

        @Override
        public void contextDestroyed(ServletContextEvent sce) {
            // Spot to close the connection pool, flush logs, etc.
            System.out.println("[App] Shutting down: " + sce.getServletContext().getContextPath());
        }
    }
}
