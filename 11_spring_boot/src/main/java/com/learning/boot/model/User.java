package com.learning.boot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/*
 * Same JPA mapping as Phases 5/9 — plus Bean Validation annotations
 * that Spring Boot's validation starter activates automatically.
 *
 *   @NotBlank @Size(...)  on String fields
 *   @Email                 on String fields holding email addresses
 *
 * In the controller, @Valid @RequestBody User u triggers these
 * checks. A failed check becomes an HTTP 400 with a helpful body.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false, length = 100)
    private String name;

    @NotBlank
    @Email
    @Size(max = 150)
    @Column(nullable = false, unique = true, length = 150)
    private String email;

    public User() { }
    public User(String name, String email) {
        this.name = name; this.email = email;
    }

    public Long getId()              { return id; }
    public void setId(Long id)       { this.id = id; }
    public String getName()          { return name; }
    public void setName(String n)    { this.name = n; }
    public String getEmail()         { return email; }
    public void setEmail(String e)   { this.email = e; }
}
