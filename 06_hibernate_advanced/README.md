# Phase 6 — Hibernate Advanced

> *Phase 5 made one entity work. Phase 6 makes them work TOGETHER — and shows you the two performance traps every team falls into.*

The `users` / `products` / `orders` schema we set up in Phase 0 has foreign keys: `orders.user_id → users.id`, `orders.product_id → products.id`. In code, that's a `User` who *has many* `Order`s, and an `Order` that *belongs to* a `Product`. Mapping these associations is what this phase is about.

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [Step1_OneToMany.java](src/main/java/com/learning/hibernateadv/Step1_OneToMany.java) | `@OneToMany` + `mappedBy` + cascades |
| 2 | [Step2_ManyToOne.java](src/main/java/com/learning/hibernateadv/Step2_ManyToOne.java) | `@ManyToOne` + `@JoinColumn` — the owning side |
| 3 | [Step3_LazyVsEager.java](src/main/java/com/learning/hibernateadv/Step3_LazyVsEager.java) | `FetchType.LAZY` vs `EAGER` and the N+1 query problem |
| 4 | [Step4_FetchJoin.java](src/main/java/com/learning/hibernateadv/Step4_FetchJoin.java) | `JOIN FETCH` — the standard N+1 fix |
| 5 | [Step5_SecondLevelCache.java](src/main/java/com/learning/hibernateadv/Step5_SecondLevelCache.java) | `@Cacheable` + 2nd-level cache regions |

---

## The two sides of an association

Every association has two ends. Pick which side OWNS the foreign key:

```
Owning side          (the one that holds the FK column)
   @ManyToOne
   @JoinColumn(name = "user_id")
   private User user;

Inverse side         (says: "look at the other side; that's the source of truth")
   @OneToMany(mappedBy = "user")
   private List<Order> orders;
```

`mappedBy` is the keyword that tells Hibernate "I'm not the owning side — `user_id` lives on the `Order` table". Forget it and Hibernate will create a *third* join table, which is almost never what you want.

---

## The N+1 problem (the most-asked Hibernate interview question)

```
1 query :  SELECT * FROM users;                   → returns 100 users
N queries: for each user, SELECT * FROM orders WHERE user_id = ?   → 100 more queries
─────────
1 + 100 round-trips for what should be ONE join.
```

This is what happens by default with **eager `@OneToMany`** or **lazy access in a loop**. The fix is `JOIN FETCH`:

```sql
FROM User u
LEFT JOIN FETCH u.orders
```

One query. One round-trip. Step 4 demonstrates this directly.

---

## When to choose what

| Setting | Default? | Use when |
|---------|----------|----------|
| `@ManyToOne` `LAZY`  | yes (in JPA spec — but Hibernate often loads `EAGER` unless told) | almost always |
| `@OneToMany` `LAZY`  | yes | almost always |
| `EAGER`              | no  | small reference data you ALWAYS need with the parent |
| `JOIN FETCH`         | n/a | you need lazy children for *this specific query* |
| 2nd-level cache      | no  | rarely-changing reference data with high read traffic |

Default everything to `LAZY`. Use `JOIN FETCH` per-query to grab what you need. That avoids both N+1 *and* over-fetching.

---

## How to run

```powershell
mvn clean compile
mvn exec:java -Dexec.mainClass="com.learning.hibernateadv.Step3_LazyVsEager"
```

Watch the SQL output. Step 3 will fire many SELECTs; Step 4 will fire one — the difference is the entire lesson.

---

> Prerequisite: complete [../05_hibernate_basics/](../05_hibernate_basics/).
> Next phase: [../07_spring_framework/](../07_spring_framework/)
