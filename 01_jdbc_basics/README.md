# Phase 1 — JDBC Basics

> *JDBC is the bottom layer. Every database tool built in Java sits on top of it — Hibernate, Spring Data, even Spring Boot's auto-config. Understand JDBC and the rest stops being magic.*

---

## The 5 steps of JDBC (memorize these)

Every JDBC program — no matter how simple or complex — follows the same five steps. Every later abstraction (Hibernate, JdbcTemplate, JPA) is just a way to write fewer lines while still doing these same five things.

```
1. Load the driver       Class.forName("com.mysql.cj.jdbc.Driver")     ← optional in modern JDBC
2. Get a connection      DriverManager.getConnection(url, user, pass)
3. Create a statement    connection.createStatement()
4. Execute the query     statement.executeQuery("SELECT ...")
5. Close everything      resultSet.close(); statement.close(); connection.close()
```

Picture the flow:

```
   Your Java code
        │
        ▼
   ┌──────────┐
   │   JDBC   │      <-- standard API (java.sql.*)
   └──────────┘
        │
        ▼
   ┌──────────┐
   │  Driver  │      <-- vendor JAR (mysql-connector-j)
   └──────────┘
        │
        ▼
     Database
```

JDBC is the **API**. The driver is the **implementation** for a specific database. Swapping MySQL for PostgreSQL means swapping the driver JAR — your JDBC code is mostly unchanged.

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [Step1_LoadDriver.java](src/main/java/com/learning/jdbc/Step1_LoadDriver.java) | Loading the driver — and why modern JDBC made it optional |
| 2 | [Step2_GetConnection.java](src/main/java/com/learning/jdbc/Step2_GetConnection.java) | `DriverManager.getConnection()` — the JDBC URL anatomy |
| 3 | [Step3_Statement.java](src/main/java/com/learning/jdbc/Step3_Statement.java) | `Statement` + `executeQuery()` + iterating a `ResultSet` |
| 4 | [Step4_InsertUpdateDelete.java](src/main/java/com/learning/jdbc/Step4_InsertUpdateDelete.java) | `executeUpdate()` — INSERT, UPDATE, DELETE |
| 5 | [Step5_CloseResources.java](src/main/java/com/learning/jdbc/Step5_CloseResources.java) | try-with-resources and why leaks bite in production |

---

## How to run

```powershell
mvn clean compile
mvn exec:java -Dexec.mainClass="com.learning.jdbc.Step3_Statement"
```

Replace the class name to run any other step.

---

## Key classes — the JDBC vocabulary

| Class | Role |
|-------|------|
| `DriverManager` | Factory that hands out `Connection` objects |
| `Connection`    | An open conversation with the database |
| `Statement`     | A simple SQL query (use only when SQL is fully static) |
| `PreparedStatement` | A SQL query with `?` placeholders — covered in Phase 2 |
| `ResultSet`     | A cursor over the rows returned by a `SELECT` |
| `SQLException`  | The checked exception every JDBC method throws |

---

> Prerequisite: complete [../00_setup/](../00_setup/) so `learning_db` exists.
> Next phase: [../02_jdbc_advanced/](../02_jdbc_advanced/)
