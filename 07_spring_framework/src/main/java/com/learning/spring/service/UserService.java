package com.learning.spring.service;

import com.learning.spring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/*
 * The "service" layer — orchestrates repositories and applies business logic.
 *
 * Constructor injection is the recommended DI style:
 *   - The dependency is FINAL (immutable, thread-safe).
 *   - The class is testable without a Spring container — just `new UserService(mock)`.
 *   - Missing dependencies fail at startup, not at first call.
 *
 * Field injection (@Autowired on fields) is concise but undermines
 * all three. Avoid it in production code.
 *
 * @Autowired on the constructor is OPTIONAL since Spring 4.3 — if
 * a class has exactly one constructor, Spring auto-wires it.
 * Including it here for clarity.
 */
@Service
public class UserService {

    private final UserRepository repository;

    @Autowired
    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<String> listAll() {
        return repository.findAll();
    }

    public void register(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        repository.save(name);
    }
}
