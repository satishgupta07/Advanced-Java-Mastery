package com.learning.boot.controller;

import com.learning.boot.model.User;
import com.learning.boot.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

/*
 * Same controller shape as Phase 10 — except:
 *   - No WebConfig, no AppInitializer, no view resolver, no web.xml.
 *   - @Valid integrates with the Bean Validation annotations on User.
 *   - Boot's auto-configured Jackson serializes responses to JSON.
 *
 * Bring up the app with `mvn spring-boot:run` and probe with curl:
 *
 *   curl -i  http://localhost:8080/api/users
 *   curl -i -X POST http://localhost:8080/api/users \
 *        -H "Content-Type: application/json" \
 *        -d '{"name":"Liam","email":"liam@example.com"}'
 *   curl -i -X DELETE http://localhost:8080/api/users/4
 */
@RestController
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService service;

    public UserRestController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<User> all() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> one(@PathVariable Long id) {
        return service.findById(id)
            .map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<User> create(@Valid @RequestBody User u) {
        // @Valid runs the Bean Validation rules; failures → 400 automatically.
        User saved = service.register(u);
        return ResponseEntity
            .created(URI.create("/api/users/" + saved.getId()))
            .body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
