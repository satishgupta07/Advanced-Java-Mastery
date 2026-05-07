# Phase 7 — Spring Framework (IoC, DI, AOP)

> *Spring's first big idea: stop writing `new` everywhere. Let a container build the object graph for you.*

Phase 5 and 6 already used Spring's ideas implicitly — Hibernate's `SessionFactory` is a heavyweight singleton you build once, and that's exactly the problem Spring's IoC container automates for *every* such object in your application.

---

## The "new everywhere" problem

Without a framework, every layer creates the layer below:

```java
class OrderController {
    private final OrderService service = new OrderService(
        new OrderRepository(new HikariDataSource(...))   // each layer wires the next
    );
}
```

Three problems:
1. **Tight coupling** — `OrderController` can't be tested without a real `HikariDataSource`.
2. **Configuration creep** — every test, every entry point repeats the wiring.
3. **No lifecycle** — who closes the data source?

---

## Inversion of Control + Dependency Injection

**IoC** = the framework controls object creation, not your code.
**DI** = each object DECLARES what it needs; the framework PROVIDES it.

```java
@Component
class OrderService {
    private final OrderRepository repo;

    @Autowired                            // "I need an OrderRepository"
    OrderService(OrderRepository repo) {  // Spring hands one in
        this.repo = repo;
    }
}
```

Your code says "I need X". The container says "here it is". You never call `new OrderRepository(...)`.

---

## The three ways to configure Spring

Spring has accumulated three configuration styles over its lifetime. They all do the same thing — declare beans and how they're wired.

| Style | Era | Example |
|-------|-----|---------|
| **XML**         | Spring 1-2 | `<bean id="..." class="..."/>` in `applicationContext.xml` |
| **Annotation**  | Spring 2.5+ | `@Component`, `@Autowired`, `@ComponentScan` |
| **Java Config** | Spring 3+   | `@Configuration` class with `@Bean` methods |

Modern code uses **Java Config + annotations**. We cover XML in Step 2 only because you'll see it in legacy projects forever — Spring Boot generates Java config under the hood.

---

## Steps in this phase

| # | File | Topic |
|---|------|-------|
| 1 | [Step1_NewEverywhere.java](src/main/java/com/learning/spring/Step1_NewEverywhere.java) | Life before DI — the manual-wiring smell |
| 2 | [Step2_XmlBeans.java](src/main/java/com/learning/spring/Step2_XmlBeans.java) | XML configuration (`applicationContext.xml`) |
| 3 | [Step3_AnnotationBeans.java](src/main/java/com/learning/spring/Step3_AnnotationBeans.java) | `@Component` + `@ComponentScan` + constructor `@Autowired` |
| 4 | [Step4_JavaConfig.java](src/main/java/com/learning/spring/Step4_JavaConfig.java) | `@Configuration` + `@Bean` (the modern default) |
| 5 | [Step5_Aop.java](src/main/java/com/learning/spring/Step5_Aop.java) | `@Aspect` + `@Around` — cross-cutting concerns |

---

## AOP in 60 seconds

Some logic doesn't belong in any one class — logging, transactions, security, metrics. **AOP** (Aspect-Oriented Programming) lets you declare it once and have Spring "weave" it around chosen methods:

```
   @Around("execution(* service..*(..))")
   public Object logCalls(ProceedingJoinPoint pjp) throws Throwable {
       long start = nanoTime();
       Object result = pjp.proceed();           // call the actual method
       log.info("{} took {}ms", pjp, (nanoTime() - start)/1_000_000);
       return result;
   }
```

Every method matched by the pointcut gets logged automatically — no edits to those methods. Spring's `@Transactional` is the most-used AOP feature in the world; you've used AOP without knowing it.

---

> Prerequisite: complete [../02_jdbc_advanced/](../02_jdbc_advanced/) — the service/repo pattern is reused.
> Next phase: [../08_spring_jdbc/](../08_spring_jdbc/)
