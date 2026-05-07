<%-- ======================================================================
     Step 4: The view — Model 2 style.

     Notice what is NOT in this file:
       - No <% %> scriptlets
       - No SQL
       - No DriverManager / PreparedStatement
       - No business logic

     The JSP only knows: "the controller put a `users` list on the
     request; render it." That separation is exactly what Spring MVC's
     @Controller + view template does in Phase 10.

     The file lives under /WEB-INF/ — Tomcat refuses to serve anything
     under WEB-INF directly to the browser, which is what we want: this
     view is reachable only through UserListServlet.
====================================================================== --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head><title>Users</title></head>
<body>
    <h1>Users (Model 2 MVC)</h1>

    <table border="1" cellpadding="6">
        <tr><th>id</th><th>name</th><th>email</th></tr>

        <c:forEach var="u" items="${users}">
            <tr>
                <td>${u.id}</td>
                <td>${u.name}</td>
                <td>${u.email}</td>
            </tr>
        </c:forEach>
    </table>

    <c:if test="${empty users}">
        <p>No users found.</p>
    </c:if>
</body>
</html>
