package com.learning.springmvc.controller;

import com.learning.springmvc.model.User;
import com.learning.springmvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/*
 * Step 5: a REST endpoint.
 *
 *   @RestController = @Controller + @ResponseBody on every method.
 *
 * The return value is NOT a view name — Spring serializes it directly
 * to JSON via Jackson. Same UserService, different output format.
 *
 *   GET  /api/users          → JSON list
 *   GET  /api/users/{id}     → JSON single user, or 404
 *   POST /api/users          → JSON body in, JSON body out
 *
 * REST semantics in three lines:
 *   @PathVariable Long id    : pulled from /users/{id}
 *   @RequestBody User u      : Jackson deserializes the request body
 *   ResponseEntity<...>      : full control of status code + headers
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService service;

    @Autowired
    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> list() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> one(@PathVariable Long id) {
        return service.findAll().stream()
            .filter(u -> u.getId().equals(id))
            .findFirst()
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> create(@RequestBody User u) {
        User saved = service.register(u.getName(), u.getEmail());
        return ResponseEntity.status(201).body(saved);
    }
}
