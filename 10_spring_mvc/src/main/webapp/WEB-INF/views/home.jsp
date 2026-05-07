<%@ page contentType="text/html; charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head><title>Spring MVC</title></head>
<body>
    <h1>${message}</h1>
    <ul>
        <li><a href="users">JSP-rendered users (Model 2)</a></li>
        <li><a href="api/users">JSON users (REST)</a></li>
    </ul>
</body>
</html>
