# Phase 9 — Spring + Hibernate

> *Hibernate gives you the ORM. Spring gives you the lifecycle, the transactions, and the dependency wiring. Together they're the most-used backend stack in the Java world.*

Phase 5 built a `SessionFactory` by hand in `HibernateUtil` and managed transactions with explicit `tx.beginTransaction()` / `tx.commit()`. Phase 8 used `@Transactional` over JDBC. This phase combines them: Spring builds and owns the `SessionFactory`, and `@Transactional` controls Hibernate's transactions for you.

---

## What changes vs. Phase 5

| Concern | Phase 5 (raw Hibernate) | Phase 9 (Spring + Hibernate) |
|---------|--------------------------|------------------------------|
| `SessionFactory` build  | `new Configuration().configure().buildSessionFactory()` in `HibernateUtil` | `LocalSessionFactoryBean` registered as a Spring `@Bean` |
| `Session` per unit-of-work | `sf.openSession()` in every method | `sessionFactory.getCurrentSession()` — tx-bound |
| Transactions | `tx = session.beginTransaction(); ... tx.commit();` | `@Transactional` on the service method |
| Config        | `hibernate.cfg.xml` | Java config (or `application.properties`) |
| Exception type | `HibernateException` (unchecked) | translated to Spring's `DataAccessException` |

---

## The key Spring beans

```java
@Bean
public LocalSessionFactoryBean sessionFactory(DataSource ds) {
    LocalSessionFactoryBean sf = new LocalSessionFactoryBean();
    sf.setDataSource(ds);
    sf.setPackagesToScan("com.learning.springhib.model");   // scans for @Entity
    sf.setHibernateProperties(hibernateProps());
    return sf;
}

@Bean
public PlatformTransactionManager txManager(SessionFactory sf) {
    return new HibernateTransactionManager(sf);             // NOT DataSourceTransactionManager
}
```

Two non-obvious points:
1. **Use `HibernateTransactionManager`, not `DataSourceTransactionManager`.** Hibernate has its own concept of "current session bound to current tx" — only the Hibernate-aware tx manager wires that correctly.
2. **`getCurrentSession()` over `openSession()`.** Inside a `@Transactional` method, `getCurrentSession()` returns the same session for the whole tx; `openSession()` creates a fresh one (and you'd have to close it yourself).

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [Step1_SpringManagedSessionFactory.java](src/main/java/com/learning/springhib/Step1_SpringManagedSessionFactory.java) | Spring builds `SessionFactory`; we just inject it |
| 2 | [Step2_TransactionalService.java](src/main/java/com/learning/springhib/Step2_TransactionalService.java) | `@Transactional` + `currentSession()` — the modern way |
| 3 | [Step3_HibernateTemplateLegacy.java](src/main/java/com/learning/springhib/Step3_HibernateTemplateLegacy.java) | Note on the legacy `HibernateTemplate` you'll see in old code |

---

## How to run

```powershell
mvn clean compile
mvn exec:java -Dexec.mainClass="com.learning.springhib.Step2_TransactionalService"
```

---

> Prerequisite: complete [../05_hibernate_basics/](../05_hibernate_basics/) and [../08_spring_jdbc/](../08_spring_jdbc/).
> Next phase: [../10_spring_mvc/](../10_spring_mvc/)
