package com.learning.hibernate.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

/*
 * Compare this to Phase 2's User POJO. Same fields, same getters/setters —
 * the ONLY difference is the annotations. That's the bargain ORMs make:
 * a few annotations in exchange for SQL-by-hand.
 *
 * Annotation cheat sheet:
 *   @Entity                — "this class maps to a database row"
 *   @Table(name = "users") — defaults to class name; override when they differ
 *   @Id                    — marks the primary-key field
 *   @GeneratedValue        — "the DB generates this id; don't set it yourself"
 *   @Column(name = "...")  — defaults to field name; override when they differ
 *
 * No-arg constructor + getters + setters are REQUIRED — Hibernate uses
 * reflection to instantiate and populate entities.
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)   // matches MySQL AUTO_INCREMENT
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, unique = true, length = 150)
    private String email;

    @Column(name = "created_at", insertable = false, updatable = false)
    private Instant createdAt;            // populated by the DB DEFAULT

    public User() { }                     // REQUIRED no-arg constructor

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Long getId()              { return id; }
    public void setId(Long id)       { this.id = id; }
    public String getName()          { return name; }
    public void setName(String n)    { this.name = n; }
    public String getEmail()         { return email; }
    public void setEmail(String e)   { this.email = e; }
    public Instant getCreatedAt()    { return createdAt; }

    @Override
    public String toString() {
        return "User{id=" + id + ", name='" + name + "', email='" + email + "'}";
    }
}
