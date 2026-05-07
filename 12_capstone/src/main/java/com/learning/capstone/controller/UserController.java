package com.learning.capstone.controller;

import com.learning.capstone.model.User;
import com.learning.capstone.repository.UserRepository;
import com.learning.capstone.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository repo;

    public UserController(UserRepository repo) { this.repo = repo; }

    @GetMapping
    public List<User> all() {
        return repo.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> one(@PathVariable Long id) {
        return repo.findById(id)
            .map(ResponseEntity::ok)
            .orElseThrow(() -> new OrderService.NotFoundException("User " + id + " not found"));
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User u) {
        User saved = repo.save(u);
        return ResponseEntity.created(URI.create("/api/users/" + saved.getId())).body(saved);
    }
}
