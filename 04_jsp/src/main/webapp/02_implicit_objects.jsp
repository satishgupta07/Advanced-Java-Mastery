<%-- ======================================================================
     Step 2: JSP Implicit Objects.

     The container injects nine pre-named variables into every JSP, so
     you don't have to declare them. Most-used five:

         request      → HttpServletRequest  (per-request data: params, headers)
         response     → HttpServletResponse (set status, headers, cookies)
         session      → HttpSession         (per-user, across requests)
         out          → JspWriter           (write to response body)
         application  → ServletContext      (per-app, shared across all users)

     Less-used four: pageContext, page, config, exception.
====================================================================== --%>
<%@ page contentType="text/html; charset=UTF-8" %>

<%
    // Bump per-session and per-app counters.
    Integer userVisits = (Integer) session.getAttribute("userVisits");
    if (userVisits == null) userVisits = 0;
    session.setAttribute("userVisits", ++userVisits);

    Integer appVisits = (Integer) application.getAttribute("appVisits");
    if (appVisits == null) appVisits = 0;
    application.setAttribute("appVisits", ++appVisits);
%>

<!DOCTYPE html>
<html>
<head><title>JSP Implicit Objects</title></head>
<body>
    <h1>Implicit Objects</h1>

    <h3>From <code>request</code> (per request)</h3>
    <ul>
        <li>Method:  <%= request.getMethod() %></li>
        <li>URI:     <%= request.getRequestURI() %></li>
        <li>Remote:  <%= request.getRemoteAddr() %></li>
    </ul>

    <h3>From <code>session</code> (per user)</h3>
    <ul>
        <li>Session id: <%= session.getId() %></li>
        <li>Your visits this session: <%= userVisits %></li>
    </ul>

    <h3>From <code>application</code> (per app, shared)</h3>
    <ul>
        <li>Total visits across all users: <%= appVisits %></li>
        <li>Context path: <%= application.getContextPath() %></li>
    </ul>
</body>
</html>
