# Phase 2 — JDBC Advanced

> *Phase 1 showed JDBC works. Phase 2 shows how to use it without making a mess.*

If Phase 1 was the "happy path", this phase covers the four things that separate a learning script from production code:

1. **`PreparedStatement`** — protects from SQL injection and lets the database cache the query plan.
2. **Transactions** — makes multiple DML statements succeed or fail together.
3. **Batch updates** — inserts a thousand rows in one round-trip instead of a thousand.
4. **The DAO pattern** — separates "how to talk to the DB" from "what your app does with the data". This is the on-ramp to Hibernate.

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [Step1_PreparedStatement.java](src/main/java/com/learning/jdbcadv/Step1_PreparedStatement.java) | `?` placeholders, type-safe setters, why injection is no longer possible |
| 2 | [Step2_ResultSet_Patterns.java](src/main/java/com/learning/jdbcadv/Step2_ResultSet_Patterns.java) | Mapping a `ResultSet` row to a domain object (`User`) |
| 3 | [Step3_Transactions.java](src/main/java/com/learning/jdbcadv/Step3_Transactions.java) | `setAutoCommit(false)` + `commit()` / `rollback()` |
| 4 | [Step4_BatchUpdates.java](src/main/java/com/learning/jdbcadv/Step4_BatchUpdates.java) | `addBatch()` + `executeBatch()` for bulk operations |
| 5 | [Step5_DAO_Pattern.java](src/main/java/com/learning/jdbcadv/Step5_DAO_Pattern.java) | The DAO refactor — the seed of every ORM that follows |
| - | [model/User.java](src/main/java/com/learning/jdbcadv/model/User.java) | A plain Java object (POJO) representing one row |
| - | [dao/UserDAO.java](src/main/java/com/learning/jdbcadv/dao/UserDAO.java) | All `users`-table SQL lives here, nowhere else |

---

## Why `PreparedStatement` over `Statement`?

```java
// UNSAFE (Phase 1 style):
String sql = "SELECT * FROM users WHERE email = '" + userInput + "'";
//                                         User types:  '; DROP TABLE users; --

// SAFE (Phase 2 style):
String sql = "SELECT * FROM users WHERE email = ?";
ps.setString(1, userInput);   // driver escapes/binds the value, never concatenates SQL
```

Two wins, not one:

- **Security** — user input is bound as a *parameter*, not concatenated as text.
- **Performance** — the database parses and plans the SQL once and caches it; every later call reuses the plan.

In real codebases, **always use `PreparedStatement`**. Raw `Statement` only earns its keep for hand-written admin queries.

---

## What is the DAO pattern?

DAO = **Data Access Object**. One Java class per table (or aggregate), with one method per operation:

```
UserDAO
  ├── save(User u)
  ├── findById(long id)
  ├── findAll()
  ├── update(User u)
  └── deleteById(long id)
```

Every other layer in your app (Servlets, Spring controllers, services) calls `UserDAO.findById(7)` and never touches `Connection`, `PreparedStatement`, or `ResultSet`.

This is the same shape Hibernate's `Session.get(User.class, 7)` and Spring Data's `userRepository.findById(7)` take — they are DAOs whose implementation is generated for you.

---

> Prerequisite: complete [../01_jdbc_basics/](../01_jdbc_basics/).
> Next phase: [../03_servlets/](../03_servlets/)
