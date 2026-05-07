# Phase 5 — Hibernate Basics

> *Hibernate's pitch: "stop translating between Java objects and database rows by hand."*

In Phase 2 you wrote a `UserDAO` whose body was 80% SQL and `ResultSet` mapping. Hibernate generates the equivalent SQL from a few annotations on your POJO and runs the result through a query plan you never see. Same domain, far less plumbing.

---

## ORM, in one paragraph

ORM stands for **Object-Relational Mapping**. The idea: declare how a Java class corresponds to a database table, then talk to the database in terms of *objects*, not SQL.

```
   Java World                         Database World
   ────────────                       ──────────────
   class    User           ←──→       table    users
   field    id             ←──→       column   id
   field    email          ←──→       column   email
   instance new User(..)   ←──→       row      INSERT ... VALUES (...)
```

Hibernate is the bridge. Once mapped, you call `session.save(user)` and Hibernate emits the right SQL.

---

## Hibernate's three core types

```
SessionFactory   — heavyweight, app-scoped singleton.
                   Reads config + entity mappings ONCE at startup.
                   Produces Sessions.

Session          — short-lived, per-unit-of-work.
                   Owns ONE Connection + a "first-level cache".
                   Like JDBC's Connection, but operating on entities.

Transaction      — boundary around a unit of work.
                   beginTransaction() → ... → commit() | rollback()
```

Every Hibernate program follows the same pattern:

```java
SessionFactory sf = HibernateUtil.getSessionFactory();   // once, at startup
try (Session s = sf.openSession()) {
    Transaction tx = s.beginTransaction();
    // ... s.save / s.get / s.createQuery ...
    tx.commit();
}
```

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [Step1_SaveEntity.java](src/main/java/com/learning/hibernate/Step1_SaveEntity.java) | `session.persist(user)` — what `INSERT` becomes |
| 2 | [Step2_GetEntity.java](src/main/java/com/learning/hibernate/Step2_GetEntity.java) | `session.get(User.class, 1L)` — primary-key fetch |
| 3 | [Step3_UpdateAndDelete.java](src/main/java/com/learning/hibernate/Step3_UpdateAndDelete.java) | Dirty checking + `session.remove()` |
| 4 | [Step4_HQL.java](src/main/java/com/learning/hibernate/Step4_HQL.java) | Hibernate Query Language — SQL-like, but on entities |
| 5 | [Step5_Criteria.java](src/main/java/com/learning/hibernate/Step5_Criteria.java) | JPA Criteria API — type-safe queries |
| - | [model/User.java](src/main/java/com/learning/hibernate/model/User.java) | Phase 2's POJO + `@Entity` annotations |
| - | [util/HibernateUtil.java](src/main/java/com/learning/hibernate/util/HibernateUtil.java) | Singleton `SessionFactory` |
| - | [hibernate.cfg.xml](src/main/resources/hibernate.cfg.xml) | DB config + dialect |

---

## Dirty checking — the move that surprises people

```java
User u = session.get(User.class, 1L);   // managed entity
u.setName("Alice (renamed)");           // no save() needed
tx.commit();                            // Hibernate diffs and issues UPDATE
```

While a `Session` is open, every entity it returned is **managed**. On commit, Hibernate snapshots vs. current values and emits `UPDATE` for anything that changed. There is no explicit `update()` call.

Once the Session closes, the entity becomes **detached** — changes to it no longer auto-sync.

---

## How to run

```powershell
mvn clean compile
mvn exec:java -Dexec.mainClass="com.learning.hibernate.Step1_SaveEntity"
```

Watch the SQL logged to console — the `show_sql=true` setting prints exactly what Hibernate generated. That visibility is non-optional during learning.

---

> Prerequisite: complete [../02_jdbc_advanced/](../02_jdbc_advanced/).
> Next phase: [../06_hibernate_advanced/](../06_hibernate_advanced/)
