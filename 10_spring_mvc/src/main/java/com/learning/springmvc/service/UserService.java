package com.learning.springmvc.service;

import com.learning.springmvc.model.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/*
 * Stub service backed by an in-memory list — keeps Phase 10 focused
 * on the MVC layer. Real apps would inject a UserRepository like
 * Phases 8-9. The shape of the controller doesn't change either way.
 */
@Service
public class UserService {

    private final AtomicLong sequence = new AtomicLong(3);
    private final List<User> users = new ArrayList<>(List.of(
        new User(1L, "Alice Johnson", "alice@example.com"),
        new User(2L, "Bob Smith",     "bob@example.com"),
        new User(3L, "Carol Davis",   "carol@example.com")
    ));

    public List<User> findAll() {
        return List.copyOf(users);
    }

    public User register(String name, String email) {
        User u = new User(sequence.incrementAndGet(), name, email);
        users.add(u);
        return u;
    }
}
