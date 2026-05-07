<%-- ======================================================================
     Step 3: Expression Language (EL) + JSTL.

     This is what modern JSP code looks like — NO scriptlets.

     EL syntax:                         JSTL tags:
        ${user.name}                       <c:if test="${...}">
        ${cart.total > 100}                <c:forEach var="item" items="${list}">
        ${empty errors}                    <c:choose><c:when><c:otherwise>

     EL automatically:
        - HTML-escapes output (XSS-safe by default)
        - Calls getters: ${user.name} → user.getName()
        - Treats nulls gracefully (no NullPointerException)

     The taglib directive at the top imports the JSTL "core" tag library
     and binds it to the prefix `c`.
====================================================================== --%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<%
    // In real code, a Servlet would do this and forward here. Inlined
    // only so this single file is self-contained and runnable.
    java.util.List<java.util.Map<String,Object>> products = java.util.List.of(
        java.util.Map.of("name", "Mechanical Keyboard", "price", 89.99,  "inStock", true),
        java.util.Map.of("name", "Wireless Mouse",      "price", 29.99,  "inStock", true),
        java.util.Map.of("name", "27-inch Monitor",     "price", 349.99, "inStock", false)
    );
    request.setAttribute("products", products);
    request.setAttribute("currentUser", "Alice");
%>

<!DOCTYPE html>
<html>
<head><title>EL + JSTL</title></head>
<body>

    <h1>Hello, ${currentUser}</h1>           <%-- EL: auto-escapes Alice --%>

    <h2>Products</h2>
    <table border="1" cellpadding="6">
        <tr><th>Name</th><th>Price</th><th>Status</th></tr>

        <%-- JSTL forEach replaces the old `<% for (... ) %>` scriptlet --%>
        <c:forEach var="p" items="${products}">
            <tr>
                <td>${p.name}</td>
                <td>$${p.price}</td>

                <%-- Conditional rendering with c:choose --%>
                <c:choose>
                    <c:when test="${p.inStock}">
                        <td style="color:green">In stock</td>
                    </c:when>
                    <c:otherwise>
                        <td style="color:red">Out</td>
                    </c:otherwise>
                </c:choose>
            </tr>
        </c:forEach>
    </table>

    <%-- c:if for one-armed conditions --%>
    <c:if test="${empty products}">
        <p>No products to display.</p>
    </c:if>

</body>
</html>
