package com.learning.spring.repository;

import org.springframework.stereotype.Repository;

import java.util.List;

/*
 * The "repository" layer — this is where data-access code lives.
 *
 * For this Phase, we keep it as an in-memory list so the example
 * stays self-contained (no DB needed). Later phases swap the body
 * for JdbcTemplate (Phase 8), then Hibernate (Phase 9), then Spring
 * Data JPA (Phase 11) — but the SHAPE is the same.
 *
 * @Repository is a specialization of @Component that:
 *   - Marks this class as a Spring-managed bean (eligible for DI).
 *   - Hints at the role (data access) — useful for AOP pointcuts.
 *   - Translates JDBC SQLExceptions to Spring's DataAccessException
 *     (only matters once we use Spring JDBC in Phase 8).
 */
@Repository
public class UserRepository {

    private final List<String> users = new java.util.ArrayList<>(
        List.of("Alice", "Bob", "Carol")
    );

    public List<String> findAll() {
        return List.copyOf(users);
    }

    public void save(String name) {
        users.add(name);
    }
}
