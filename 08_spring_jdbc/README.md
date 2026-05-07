# Phase 8 — Spring JDBC

> *Spring JDBC removes the boilerplate from raw JDBC without making you adopt an ORM. You still write SQL — just less of it.*

Phase 2's `UserDAO` had the same shape in every method:
1. Open a `Connection`.
2. Prepare a `PreparedStatement`.
3. Set parameters.
4. Walk the `ResultSet`.
5. Close everything in reverse.

Steps 1, 2, and 5 are pure ceremony. `JdbcTemplate` keeps step 3 (your params) and step 4 (your row mapping) and does the rest for you.

---

## What `JdbcTemplate` does for you

```java
// Phase 2 — raw JDBC
try (Connection c = ...; PreparedStatement ps = c.prepareStatement(sql)) {
    ps.setLong(1, id);
    try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
            return new User(rs.getLong("id"), rs.getString("name"), rs.getString("email"));
        }
    }
    return null;
}

// Phase 8 — JdbcTemplate
return jdbc.queryForObject(
    "SELECT id, name, email FROM users WHERE id = ?",
    (rs, rowNum) -> new User(rs.getLong("id"), rs.getString("name"), rs.getString("email")),
    id);
```

Same SQL. Same row mapping. The connection / statement / cursor / close ceremony is gone — and so is the verbose checked `SQLException` (Spring re-throws as unchecked `DataAccessException`).

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [Step1_JdbcTemplateQuery.java](src/main/java/com/learning/springjdbc/Step1_JdbcTemplateQuery.java) | `queryForObject`, `query`, RowMapper as lambda |
| 2 | [Step2_JdbcTemplateUpdate.java](src/main/java/com/learning/springjdbc/Step2_JdbcTemplateUpdate.java) | `update(sql, args...)` for INSERT/UPDATE/DELETE |
| 3 | [Step3_NamedParameterJdbcTemplate.java](src/main/java/com/learning/springjdbc/Step3_NamedParameterJdbcTemplate.java) | `:name` placeholders for many-arg statements |
| 4 | [Step4_TransactionalService.java](src/main/java/com/learning/springjdbc/Step4_TransactionalService.java) | `@Transactional` — declarative transactions via AOP |

---

## RowMapper — the abstraction in one line

A `RowMapper<T>` is a function: `(ResultSet, rowNum) → T`. You hand `JdbcTemplate` your mapper and it walks the cursor for you. Phase 2's `mapRow()` helper became a real interface here.

```java
RowMapper<User> mapper = (rs, n) -> new User(
    rs.getLong("id"), rs.getString("name"), rs.getString("email"));

User u   = jdbc.queryForObject("SELECT ... WHERE id = ?", mapper, 1L);
List<User> all = jdbc.query("SELECT ...", mapper);
```

---

## `@Transactional` — Spring's flagship AOP feature

Phase 7 ended with a `TimingAspect` wrapping every service method. `@Transactional` works the exact same way: an aspect that opens a transaction before your method and commits / rolls back after.

```java
@Service
public class OrderService {
    @Transactional
    public void placeOrder(...) {     // tx begins here
        productRepo.decrementStock(...);
        orderRepo.insert(...);
    }                                  // tx commits here (or rolls back on exception)
}
```

This is what Step 4 demonstrates — the same "decrement stock + create order" example from Phase 2, but the transaction is declared as a single annotation instead of `setAutoCommit(false)` + `commit()` + `rollback()` plumbing.

---

## How to run

```powershell
mvn clean compile
mvn exec:java -Dexec.mainClass="com.learning.springjdbc.Step1_JdbcTemplateQuery"
```

---

> Prerequisite: complete [../07_spring_framework/](../07_spring_framework/).
> Next phase: [../09_spring_hibernate/](../09_spring_hibernate/)
