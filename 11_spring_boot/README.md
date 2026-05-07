# Phase 11 — Spring Boot

> *Spring Boot is Spring with the configuration done for you. Everything you wrote by hand in Phases 7-10 — `DataSource` bean, `SessionFactory` bean, `DispatcherServlet` registration, transaction manager — is auto-configured the moment a starter dependency lands on the classpath.*

---

## Three big ideas

### 1. Auto-configuration

Spring Boot inspects what's on the classpath and conditionally creates the beans you'd otherwise write yourself:

| You added… | Spring Boot wires up… |
|------------|------------------------|
| `spring-boot-starter-web`        | `DispatcherServlet`, embedded Tomcat, Jackson, validation |
| `spring-boot-starter-data-jpa`   | `DataSource`, `EntityManagerFactory`, `JpaTransactionManager` |
| H2/MySQL driver on the classpath | The `DataSource` is configured for that driver |

Phase 9's `AppConfig.java` was 50 lines. In Boot, those beans appear automatically.

### 2. Starters

A "starter" is a curated dependency bundle. `spring-boot-starter-data-jpa` pulls in Hibernate, JPA API, transaction support, and the right versions of all of them. You stop choosing dependency versions; Boot does.

### 3. Embedded server

Phases 3, 4, 10 produced WAR files you dropped into Tomcat. Spring Boot apps are JARs that EMBED Tomcat. `java -jar app.jar` boots the server inside the same process. `mvn spring-boot:run` does it during dev. No external Tomcat install.

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [Application.java](src/main/java/com/learning/boot/Application.java) | `@SpringBootApplication` + `main` — the whole boot stage |
| 2 | [model/User.java](src/main/java/com/learning/boot/model/User.java) | JPA entity, identical to earlier phases |
| 3 | [repository/UserRepository.java](src/main/java/com/learning/boot/repository/UserRepository.java) | `extends JpaRepository<User, Long>` — CRUD generated for you |
| 4 | [service/UserService.java](src/main/java/com/learning/boot/service/UserService.java) | `@Transactional` over the repo |
| 5 | [controller/UserRestController.java](src/main/java/com/learning/boot/controller/UserRestController.java) | REST endpoints — same as Phase 10, no config needed |
| - | [application.properties](src/main/resources/application.properties) | Datasource + JPA props in one flat file |

---

## `JpaRepository` — the magic interface

```java
public interface UserRepository extends JpaRepository<User, Long> { }
```

That's it. No implementation. At startup, Spring Data inspects the interface and generates a proxy that implements:

- `save(entity)`
- `findById(id)` → `Optional<T>`
- `findAll()`
- `deleteById(id)`
- `count()`

Plus query methods inferred from method names:

```java
List<User> findByEmail(String email);                    // generates: WHERE email = ?
List<User> findByNameContainingIgnoreCase(String part);  // generates: WHERE LOWER(name) LIKE %?%
```

Compare to:
- Phase 2's hand-written `UserDAO`
- Phase 5's `session.createQuery("FROM User WHERE ...")`
- Phase 8's `JdbcTemplate.query(sql, mapper)`
- Phase 9's `getCurrentSession().createQuery(...)`

Spring Data is the same DAO pattern — implementation generated, not written.

---

## How to run

```powershell
mvn spring-boot:run
# Server starts on :8080. Endpoints:
#   GET    http://localhost:8080/api/users
#   GET    http://localhost:8080/api/users/1
#   POST   http://localhost:8080/api/users   { "name": "...", "email": "..." }
#   DELETE http://localhost:8080/api/users/1
```

Or build and run as a fat JAR:
```powershell
mvn clean package
java -jar target/spring-boot-demo-1.0.0.jar
```

---

> Prerequisite: complete [../09_spring_hibernate/](../09_spring_hibernate/) and [../10_spring_mvc/](../10_spring_mvc/).
> Next phase: [../12_capstone/](../12_capstone/)
