<%-- ======================================================================
     Step 1: JSP basics — directives, scriptlets, expressions, declarations.

     This file deliberately uses the OLD-STYLE Java-in-JSP syntax so you
     see what Phase 3's HTML-by-string-concat turns into. The next files
     show why we abandon scriptlets in favor of EL + JSTL.

     A JSP comment (this thing) is stripped at compile time and never
     reaches the browser — unlike <!-- HTML comment -->.
====================================================================== --%>

<%-- Directive: tells the container how to compile this page. --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%-- Declaration: becomes a FIELD or METHOD on the generated Servlet class.
     Runs ONCE per Servlet instance — shared across all requests/threads. --%>
<%!
    private int hitCount = 0;
    private String greet(String name) { return "Hello, " + name + "!"; }
%>

<%-- Scriptlet: arbitrary Java that runs INSIDE the generated _jspService(). --%>
<%
    hitCount++;                                  // shared field — racy on purpose
    String visitor = request.getParameter("name");
    if (visitor == null) visitor = "stranger";
%>

<!DOCTYPE html>
<html>
<head><title>JSP Basics</title></head>
<body>

    <h1><%= greet(visitor) %></h1>     <%-- Expression: prints the value of an expression --%>
    <p>This page has been rendered <%= hitCount %> times.</p>

    <p>Try: <a href="01_basics.jsp?name=Alice">?name=Alice</a></p>

    <hr>
    <p><strong>Why we abandon this syntax:</strong>
       Mixing Java with HTML makes pages unmaintainable and untestable.
       Real JSP code uses Expression Language and JSTL — see
       <a href="03_el_jstl.jsp">03_el_jstl.jsp</a>.</p>

</body>
</html>
