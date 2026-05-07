<%-- The same JSP shape as Phase 4 — but the controller that drove it
     is now a @Controller method instead of a hand-written Servlet. --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head><title>Users</title></head>
<body>
    <h1>Users</h1>

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

    <h2>Add a user</h2>
    <form action="users" method="post">
        <input name="name"  placeholder="Name"  required>
        <input name="email" placeholder="Email" required>
        <button type="submit">Save</button>
    </form>

    <p><a href="${pageContext.request.contextPath}/">Home</a></p>
</body>
</html>
