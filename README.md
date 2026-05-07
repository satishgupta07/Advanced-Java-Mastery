# Advanced Java Mastery — Complete Learning Guide

> *From JDBC to Spring Boot — a step-wise journey through Enterprise Java.*

This repo is a curated, step-by-step path through the Java backend stack. Each phase has its own folder; each step is a numbered file you can read top-to-bottom. Code is intentionally minimal and well-commented so the *concept* is never buried under boilerplate.

---

## Quick Reference

```
Phase 0  — Prerequisites & Setup       (00_setup/)
Phase 1  — JDBC Basics                 (01_jdbc_basics/)
Phase 2  — JDBC Advanced               (02_jdbc_advanced/)
Phase 3  — Servlets                    (03_servlets/)
Phase 4  — JSP                         (04_jsp/)
Phase 5  — Hibernate Basics            (05_hibernate_basics/)
Phase 6  — Hibernate Advanced          (06_hibernate_advanced/)
Phase 7  — Spring Framework (IoC/DI)   (07_spring_framework/)
Phase 8  — Spring JDBC                 (08_spring_jdbc/)
Phase 9  — Spring + Hibernate          (09_spring_hibernate/)
Phase 10 — Spring MVC                  (10_spring_mvc/)
Phase 11 — Spring Boot                 (11_spring_boot/)
Phase 12 — Capstone Project            (12_capstone/)
```

---

## How to use this repo

1. **Read in order.** Each phase builds on the previous one. JDBC explains how the database talks to Java; Hibernate hides that talk; Spring wires it all together; Spring Boot makes Spring effortless.
2. **Run the code.** Each step is runnable. Start with a single `main()` for JDBC/Hibernate/Spring core, deploy a WAR for Servlets/JSP/Spring MVC, and use `mvn spring-boot:run` for Spring Boot.
3. **Pause and predict.** Before running a snippet, guess the output. The "aha" moments come from your guesses being wrong.

---

## The Big Picture — Why these technologies, in this order?

| Layer | Problem it solves | What it gave us |
|-------|-------------------|-----------------|
| **JDBC** | "How does Java talk to a database?" | Driver, Connection, Statement, ResultSet |
| **Servlets** | "How does Java handle an HTTP request?" | A Java class that responds to `GET`/`POST` |
| **JSP** | "Writing HTML with `out.println` is awful." | HTML files with embedded Java, compiled to Servlets |
| **Hibernate** | "JDBC is too verbose; mapping rows to objects by hand is tedious." | ORM — Java objects ↔ database rows |
| **Spring Framework** | "Wiring objects together (`new` everywhere) is brittle." | Inversion of Control + Dependency Injection |
| **Spring JDBC / Hibernate** | "Spring should remove JDBC/Hibernate boilerplate too." | `JdbcTemplate`, integrated transactions |
| **Spring MVC** | "Servlets work, but request routing should be declarative." | `@Controller`, `@RequestMapping`, view resolvers |
| **Spring Boot** | "Spring's XML config is heavy; convention should beat configuration." | Auto-configuration, starters, embedded Tomcat |

Once you see the *problem* each layer was invented to solve, the API choices stop feeling arbitrary.

---

> Each phase below has its own README inside its folder with the full walkthrough. This top-level file is the map.
