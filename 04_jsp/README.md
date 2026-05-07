# Phase 4 — JSP (JavaServer Pages)

> *A JSP is HTML that can run Java. Or — equivalently — a Servlet that's easier to write.*

Phase 3 ended with a Servlet that built an HTML table by `out.println(...)`. That gets painful fast. JSP flips the model: write HTML *first*, sprinkle in Java *only where dynamic data is needed*.

---

## How JSP actually works

```
your-page.jsp        →  the container compiles it into a Servlet
                        ( your-page_jsp.java + your-page_jsp.class )
                     →  every request runs that generated Servlet
```

A JSP is **not** a separate technology. It's a *source format* the container translates into a Servlet on first request. So everything you learned in Phase 3 — request, response, sessions, filters — applies untouched.

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [01_basics.jsp](src/main/webapp/01_basics.jsp) | Directives, scriptlets, expressions, declarations |
| 2 | [02_implicit_objects.jsp](src/main/webapp/02_implicit_objects.jsp) | `request`, `response`, `session`, `out`, `application` |
| 3 | [03_el_jstl.jsp](src/main/webapp/03_el_jstl.jsp) | Expression Language `${...}` + JSTL `<c:forEach>` |
| 4 | [UserListServlet.java](src/main/java/com/learning/jsp/UserListServlet.java) + [WEB-INF/views/users.jsp](src/main/webapp/WEB-INF/views/users.jsp) | Model 2 MVC — Servlet picks data, JSP renders |

---

## The four JSP syntax flavors

| Syntax | Means | When to use |
|--------|-------|-------------|
| `<%@ page ... %>`            | Directive — page-level config | Once at top |
| `<% ... %>`                  | Scriptlet — runs Java code     | **Avoid in modern code — use EL/JSTL** |
| `<%= expr %>`                | Expression — prints `expr`     | Avoid — use `${expr}` |
| `<%! field; method() {} %>`  | Declaration — class fields/methods | Almost never |
| `${expr}`                    | EL — Expression Language        | **Default. Safe, escaped, concise.** |
| `<c:forEach var="x" items="${list}">` | JSTL tag — control flow | **Default for loops & conditionals.** |

The "modern" rule of thumb: if you find yourself opening `<% %>` to write Java in a JSP, you're doing it wrong. Move that logic to a Servlet (or a Spring controller in later phases) and pass the result through EL.

---

## Model 2 MVC — the pattern Spring MVC formalizes

```
                   ┌── Model ──┐         ┌── Controller ──┐         ┌── View ──┐
   request ──────► │  POJOs    │ ◄────── │  Servlet       │ ──────► │   JSP    │ ──► HTML
                   │  DAO      │         │  (logic)       │         │ (markup) │
                   └───────────┘         └────────────────┘         └──────────┘
```

The Servlet:
1. Decides what data to load (model).
2. Stuffs it into `request.setAttribute("users", users)`.
3. Forwards to a JSP that renders.

The JSP:
- Knows nothing about JDBC.
- Pulls data from request attributes via `${users}`.
- Loops over them with `<c:forEach>`.

This separation — controller decides, view renders — is exactly what `@Controller` + Thymeleaf/JSP does in Spring MVC (Phase 10). You're seeing the pattern in its raw form first.

---

## How to run

```powershell
mvn clean package
# Drop target/jsp-1.0.0.war into Tomcat 10's webapps/, then visit:
#   http://localhost:8080/jsp/01_basics.jsp
#   http://localhost:8080/jsp/03_el_jstl.jsp
#   http://localhost:8080/jsp/users         ← Model 2 example
```

---

> Prerequisite: complete [../03_servlets/](../03_servlets/).
> Next phase: [../05_hibernate_basics/](../05_hibernate_basics/)
