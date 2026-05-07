# Phase 12 — Capstone: Order Management API

> *Every layer of the stack, in one small Spring Boot app.*

This phase ties the whole journey together. We build a tiny e-commerce order API on the same `users` / `products` / `orders` schema you've used since Phase 0 — except this time *every* concept earns its place:

| From phase | Concept used here |
|------------|-------------------|
| 0  | The shared `learning_db` schema |
| 2  | DAO-style separation (now via Spring Data) |
| 5  | JPA entities (`@Entity`, `@Id`, `@Column`) |
| 6  | `@OneToMany` / `@ManyToOne`, lazy fetching, `JOIN FETCH` |
| 7  | DI / IoC — every layer wired by Spring |
| 8  | `@Transactional` with rollback on insufficient stock |
| 10 | `@RestController` + `@PathVariable` + `@RequestBody` |
| 11 | Spring Boot starters, `JpaRepository`, `@Valid`, auto-config |

---

## What it does

```
GET    /api/users                       — list users
GET    /api/users/{id}                  — one user
POST   /api/users                       — create user

GET    /api/products                    — list products
GET    /api/products/{id}               — one product

GET    /api/orders                      — list all orders (with user + product joined in one query)
POST   /api/orders                      — place an order:
                                            • validates stock
                                            • decrements stock
                                            • inserts order row
                                            • all in ONE transaction
```

`POST /api/orders` is the climax: it does the same "decrement stock + insert order" dance that Phase 2 wrote with `connection.setAutoCommit(false)`, Phase 3 wrote in a Servlet, and Phase 8 wrote with `@Transactional` — here it's a clean `@Transactional` service method on top of Spring Data, fully validated.

---

## Files

| File | What it does |
|------|--------------|
| [Application.java](src/main/java/com/learning/capstone/Application.java) | `@SpringBootApplication` entry point |
| [model/User.java](src/main/java/com/learning/capstone/model/User.java) | JPA entity |
| [model/Product.java](src/main/java/com/learning/capstone/model/Product.java) | JPA entity with stock |
| [model/Order.java](src/main/java/com/learning/capstone/model/Order.java) | `@ManyToOne` to User & Product |
| [repository/UserRepository.java](src/main/java/com/learning/capstone/repository/UserRepository.java) | `JpaRepository` |
| [repository/ProductRepository.java](src/main/java/com/learning/capstone/repository/ProductRepository.java) | `JpaRepository` |
| [repository/OrderRepository.java](src/main/java/com/learning/capstone/repository/OrderRepository.java) | `JpaRepository` + `@Query` with `JOIN FETCH` |
| [service/OrderService.java](src/main/java/com/learning/capstone/service/OrderService.java) | `@Transactional placeOrder()` — the heart of the app |
| [controller/UserController.java](src/main/java/com/learning/capstone/controller/UserController.java) | REST endpoints |
| [controller/ProductController.java](src/main/java/com/learning/capstone/controller/ProductController.java) | REST endpoints |
| [controller/OrderController.java](src/main/java/com/learning/capstone/controller/OrderController.java) | REST endpoints |
| [dto/PlaceOrderRequest.java](src/main/java/com/learning/capstone/dto/PlaceOrderRequest.java) | Validated request body |
| [dto/OrderResponse.java](src/main/java/com/learning/capstone/dto/OrderResponse.java) | Output shape (no entity leaks to JSON) |
| [exception/GlobalExceptionHandler.java](src/main/java/com/learning/capstone/exception/GlobalExceptionHandler.java) | `@ControllerAdvice` → consistent error JSON |
| [requests.http](requests.http) | Curl-style sample requests |

---

## How to run

```powershell
mvn spring-boot:run
```

Then exercise the API with the calls in [requests.http](requests.http) (or the equivalent curl commands).

---

## DTOs vs entities — why both?

Returning a JPA entity directly from a `@RestController` is convenient, and a recipe for two problems:
1. **Lazy-loading exceptions in the JSON serializer.** Jackson tries to walk `order.getUser().getOrders()`, the session is closed, you get a runtime error.
2. **Accidental field leaks.** Add a `passwordHash` to `User` for a future feature → it ends up in every API response.

DTOs (`dto/OrderResponse.java`) are the explicit boundary between "what's stored" and "what's exposed". Worth the few extra lines.

---

> Prerequisite: the entire repo, top to bottom.
