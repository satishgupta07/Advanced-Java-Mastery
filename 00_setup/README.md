# Phase 0 — Prerequisites & Setup

> *Get your environment right once, and the next 12 phases just work.*

This phase installs the tools you'll use everywhere else and sets up a single shared database (`learning_db`) that every later phase will read from and write to.

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [01_install_jdk.md](01_install_jdk.md) | Install JDK 17, verify `java -version`, set `JAVA_HOME` |
| 2 | [02_maven_basics.md](02_maven_basics.md) | What Maven does, anatomy of `pom.xml`, common commands |
| 3 | [03_database_setup.md](03_database_setup.md) | Install MySQL, create the shared `learning_db` schema |
| 4 | [04_sample_schema.sql](04_sample_schema.sql) | The actual SQL — `users`, `products`, `orders` |

---

## Why one shared database?

Every phase from JDBC onwards needs a database. Instead of inventing new tables for each phase, we create **one schema** here and reuse it. This way:

- The same `users` table you `INSERT` into with raw JDBC in Phase 1, you `save()` with Hibernate in Phase 5, and expose as a REST endpoint in Phase 11.
- You see the same domain reshaped through every layer — that's where the learning lives.

---

## Tools at a glance

| Tool | Version | Why |
|------|---------|-----|
| JDK | 17 (LTS) | Required by modern Spring Boot 3.x |
| Maven | 3.9+ | Build tool; manages dependencies |
| MySQL | 8.x | Database (PostgreSQL also works — driver is the only difference) |
| IDE | IntelliJ IDEA / VS Code / Eclipse | Any will do |
| Tomcat | 10.x (only for Phases 3-4, 10) | Servlet container; Spring Boot embeds its own |

---

> Once you've finished this phase, your next stop is [../01_jdbc_basics/](../01_jdbc_basics/).
