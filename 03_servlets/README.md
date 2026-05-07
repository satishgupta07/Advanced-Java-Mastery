# Phase 3 — Servlets

> *A Servlet is a Java class that responds to an HTTP request. That is the whole idea — everything else is plumbing.*

Before Spring MVC, before JSP, before any framework existed, Java handled HTTP through Servlets. Every modern Java web framework — Spring MVC, Spring Boot, JSP — is ultimately a layer on top of Servlets. Knowing what they do shows you what frameworks are saving you from.

---

## The mental model

```
Browser           Servlet Container (Tomcat)         Your Servlet
┌──────┐    GET     ┌──────────────────────┐         ┌──────────────────┐
│      │ ─────────► │  HTTP parser         │ ──────► │ doGet(req, res)  │
│      │            │  Thread per request  │         │ ... your code ... │
│      │ ◄───────── │  Response writer     │ ◄────── │ res.getWriter()  │
└──────┘   200 OK   └──────────────────────┘         └──────────────────┘
```

Tomcat does the heavy lifting:
- Parses raw HTTP into `HttpServletRequest`
- Creates `HttpServletResponse` for you to fill
- Hands them to the Servlet whose URL pattern matches
- Serializes your response back to the wire

You write the Servlet. Tomcat handles HTTP.

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [Step1_HelloServlet.java](src/main/java/com/learning/servlets/Step1_HelloServlet.java) | `doGet`, writing HTML to the response |
| 2 | [Step2_FormHandlingServlet.java](src/main/java/com/learning/servlets/Step2_FormHandlingServlet.java) | `doPost`, reading form parameters |
| 3 | [Step3_SessionServlet.java](src/main/java/com/learning/servlets/Step3_SessionServlet.java) | `HttpSession` — keeping state across requests |
| 4 | [Step4_FilterAndListener.java](src/main/java/com/learning/servlets/Step4_FilterAndListener.java) | Filters + lifecycle listeners (cross-cutting concerns) |
| 5 | [Step5_ServletWithUserDAO.java](src/main/java/com/learning/servlets/Step5_ServletWithUserDAO.java) | Reuses Phase 2's `UserDAO` to render real DB data |
| - | [src/main/webapp/index.html](src/main/webapp/index.html) | Tiny form that POSTs to Step 2 |
| - | [src/main/webapp/WEB-INF/web.xml](src/main/webapp/WEB-INF/web.xml) | Deployment descriptor (the XML alternative to `@WebServlet`) |

---

## Servlet lifecycle (memorize)

```
init()        — called once, when the container loads the Servlet
doGet/doPost  — called once PER REQUEST, possibly on many threads at once
destroy()     — called once, when the container shuts down
```

Two consequences:
1. **`init()` is your one-time setup hook.** Open a connection pool here, not in `doGet`.
2. **`doGet`/`doPost` run concurrently on shared instance fields.** Servlets are not thread-safe by default. Keep request data in local variables, not instance fields.

---

## How to run

```powershell
mvn clean package
# Produces target/servlets-1.0.0.war — drop into Tomcat 10's webapps/, then visit:
#   http://localhost:8080/servlets/hello
#   http://localhost:8080/servlets/index.html
```

Servlet 6.0 / Jakarta requires Tomcat 10+ (the `javax.servlet` → `jakarta.servlet` rename happened here — older tutorials using `javax.*` will not compile against Jakarta).

---

> Prerequisite: complete [../02_jdbc_advanced/](../02_jdbc_advanced/) — Step 5 reuses its `UserDAO`.
> Next phase: [../04_jsp/](../04_jsp/)
